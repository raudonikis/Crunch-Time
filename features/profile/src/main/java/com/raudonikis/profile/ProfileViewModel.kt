package com.raudonikis.profile

import androidx.lifecycle.ViewModel
import com.raudonikis.data_domain.activity.repo.UserActivityRepository
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.profile.activity.ActivitiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val userActivityRepository: UserActivityRepository,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    /**
     * States
     */
    private val _activitiesState: MutableStateFlow<ActivitiesState> =
        MutableStateFlow(ActivitiesState.Initial)


    /**
     * Observables
     */
    val activitiesState: StateFlow<ActivitiesState> = _activitiesState

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