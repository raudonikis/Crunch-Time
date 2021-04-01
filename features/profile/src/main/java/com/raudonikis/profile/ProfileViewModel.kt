package com.raudonikis.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.repo.UserActivityRepository
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.repo.UserRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.profile.activity.ActivitiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val userActivityRepository: UserActivityRepository,
    private val gamesRepository: GamesRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    init {
        updateFollowingUsers()
    }

    /**
     * States
     */
    private val _activitiesState: MutableStateFlow<ActivitiesState> =
        MutableStateFlow(ActivitiesState.Initial)

    /**
     * Observables
     */
    val activitiesState: Flow<ActivitiesState> = _activitiesState
    val followingUsersState: Flow<Outcome<List<User>>> = userRepository.getFollowingUsers()

    /**
     * Followers
     */
    private fun updateFollowingUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateFollowingUsers()
        }
    }

    /**
     * Events
     */

    fun onGameClicked(game: Game) {
        navigateToDetails(game)
    }

    /**
     * Navigation
     */

    private fun navigateToDetails(game: Game) {
        navigationDispatcher.navigate(ProfileRouter.profileToDetails(game))
    }
}