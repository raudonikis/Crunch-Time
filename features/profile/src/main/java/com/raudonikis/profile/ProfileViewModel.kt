package com.raudonikis.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.repo.ActivitiesRepository
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.profile.activity.ActivitiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val activitiesRepository: ActivitiesRepository,
) : ViewModel() {

    /**
     * States
     */
    private val _activitiesState: MutableStateFlow<ActivitiesState> =
        MutableStateFlow(ActivitiesState.Initial)

    /**
     * Initialisation
     */
    init {
        updateActivities()
    }

    /**
     * Observables
     */
    val activitiesState: StateFlow<ActivitiesState> = _activitiesState

    /**
     * Events
     */
    fun onActivityClick(activity: UserActivity) {
        // todo
    }

    fun onCollectionClicked(gameStatus: GameStatus) {
        navigateToGameCollection(gameStatus)
    }

    /**
     * Activities
     */
    private fun updateActivities() {
        _activitiesState.value = ActivitiesState.Loading
        viewModelScope.launch {
            activitiesRepository.getUserActivities()
                .onSuccess {
                    _activitiesState.value = ActivitiesState.Success(it)
                }
                .onFailure {
                    _activitiesState.value = ActivitiesState.Failure
                }
                .onEmpty {
                    _activitiesState.value = ActivitiesState.Failure
                }
        }
    }

    /**
     * Navigation
     */
    private fun navigateToGameCollection(gameStatus: GameStatus) {
        navigationDispatcher.navigate(ProfileRouter.profileToGameCollection(gameStatus))
    }
}