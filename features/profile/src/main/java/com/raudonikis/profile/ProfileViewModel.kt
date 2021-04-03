package com.raudonikis.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.usecases.MyActivityUseCase
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.data_domain.game_collection.GameCollectionUseCase
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.data_domain.user_following.UserFollowingUseCase
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import com.raudonikis.profile.followers.FollowerType
import com.raudonikis.profile.logout.LogoutEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val userPreferences: UserPreferences,
    private val authenticationRepository: AuthenticationRepository,
    // Use cases
    private val gameCollectionUseCase: GameCollectionUseCase,
    private val userFollowingUseCase: UserFollowingUseCase,
    private val myActivityUseCase: MyActivityUseCase,
) : ViewModel() {

    /**
     * States/Events
     */
    private val _userState: MutableStateFlow<Outcome<User>> = MutableStateFlow(Outcome.empty())
    private val _gameCollectionTypeState: MutableStateFlow<GameCollectionType> =
        MutableStateFlow(GameCollectionType.PLAYED)
    private val _logoutEvent: Channel<LogoutEvent> = Channel()

    init {
        updateGameCollections()
        updateFollowingUsers()
        updateCurrentUser()
        updateMyActivity()
    }

    /**
     * Observables
     */
    val myActivityState: Flow<Outcome<List<UserActivity>>> = myActivityUseCase.getMyUserActivity()
    val followingUsersState: Flow<Outcome<List<User>>> = userFollowingUseCase.getFollowingUsers()
    val userState: Flow<Outcome<User>> = _userState
    val gameCollectionTypeState: StateFlow<GameCollectionType> = _gameCollectionTypeState
    val gameCollection: Flow<Outcome<List<Game>>> =
        _gameCollectionTypeState.flatMapLatest { gameCollectionType ->
            gameCollectionUseCase.getGameCollection(gameCollectionType)
        }
    val logoutEvent: Flow<LogoutEvent> = _logoutEvent.receiveAsFlow()

    /**
     * Followers
     */
    private fun updateFollowingUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userFollowingUseCase.updateFollowingUsers()
        }
    }

    /**
     * User
     */
    private fun updateCurrentUser() {
        userPreferences.currentUser?.let { user ->
            _userState.value = Outcome.success(user)
        }
    }

    /**
     * Game collection
     */
    private fun updateGameCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            gameCollectionUseCase.updateGameCollection()
        }
    }

    /**
     * My activity
     */
    private fun updateMyActivity() {
        viewModelScope.launch(Dispatchers.IO) {
            myActivityUseCase.updateMyUserActivity()
        }
    }

    /**
     * Events
     */

    fun onGameClicked(game: Game) {
        navigateToDetails(game)
    }

    fun onFollowersClicked() {
        navigateToFollowers()
    }

    fun onFollowingClicked() {
        navigateToFollowing()
    }

    fun onGameCollectionTabSwitched(gameCollectionType: GameCollectionType?) {
        gameCollectionType ?: return
        _gameCollectionTypeState.value = gameCollectionType
    }

    fun onLogoutClicked() {
        _logoutEvent.offer(LogoutEvent.IN_PROGRESS)
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.logout()
                .onSuccess {
                    _logoutEvent.offer(LogoutEvent.SUCCESS)
                    navigateToLogin()
                }
                .onFailure { _logoutEvent.offer(LogoutEvent.FAILURE) }
        }
    }

    /**
     * Navigation
     */
    private fun navigateToDetails(game: Game) {
        navigationDispatcher.navigate(ProfileRouter.profileToDetails(game))
    }

    private fun navigateToFollowers() {
        navigationDispatcher.navigate(ProfileRouter.profileToFollowers(FollowerType.FOLLOWER))
    }

    private fun navigateToFollowing() {
        navigationDispatcher.navigate(ProfileRouter.profileToFollowers(FollowerType.FOLLOWING))
    }

    private fun navigateToLogin() {
        navigationDispatcher.navigate(NavigationGraph.Login)
    }
}