package com.raudonikis.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.data_domain.user.repo.UserRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.profile.activity.ActivitiesState
import com.raudonikis.profile.followers.FollowerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    /**
     * States
     */
    private val _activitiesState: MutableStateFlow<ActivitiesState> =
        MutableStateFlow(ActivitiesState.Initial)
    private val _userState: MutableStateFlow<Outcome<User>> = MutableStateFlow(Outcome.empty())
    private val _gameCollectionTypeState: MutableStateFlow<GameCollectionType> =
        MutableStateFlow(GameCollectionType.PLAYED)

    init {
        updateGameCollections()
        updateFollowingUsers()
        updateCurrentUser()
    }

    /**
     * Observables
     */
    val activitiesState: Flow<ActivitiesState> = _activitiesState
    val followingUsersState: Flow<Outcome<List<User>>> = userRepository.getFollowingUsers()
    val userState: Flow<Outcome<User>> = _userState
    val gameCollectionTypeState: StateFlow<GameCollectionType> = _gameCollectionTypeState
    val gameCollection: Flow<Outcome<List<Game>>> =
        _gameCollectionTypeState.flatMapLatest { gameCollectionType ->
            gamesRepository.getGameCollection(gameCollectionType)
        }

    /**
     * Followers
     */
    private fun updateFollowingUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateFollowingUsers()
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
            listOf(
                GameCollectionType.WANT,
                GameCollectionType.PLAYING,
                GameCollectionType.PLAYED
            ).map {
                gamesRepository.updateGameCollection(it)
            }
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

    fun onGameCollectionTabSwitched(gameCollectionType: GameCollectionType) {
        _gameCollectionTypeState.value = gameCollectionType
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
}