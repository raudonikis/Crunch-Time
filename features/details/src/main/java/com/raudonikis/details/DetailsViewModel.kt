package com.raudonikis.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    /**
     * States
     */
    private val _detailsState: MutableStateFlow<DetailsState> =
        MutableStateFlow(DetailsState.Initial)
    private val _currentGame: MutableStateFlow<Game> = MutableStateFlow(Game())

    /**
     * Observables
     */
    val detailsState: StateFlow<DetailsState> = _detailsState
    val currentGame: StateFlow<Game> = _currentGame

    /**
     * Initialisation
     */
    fun onCreate(game: Game) {
        _currentGame.value = game
        if (game.isUpdateNeeded) {
            updateGameDetails()
        }
    }

    /**
     * Details functionality
     */
    fun updateGameStatus(status: GameStatus) {
        if (status == currentGame.value.status || status == GameStatus.EMPTY) {
            _detailsState.value = DetailsState.StatusUpdateNotNeeded
            return
        }
        _detailsState.value = DetailsState.StatusUpdating
        viewModelScope.launch(Dispatchers.IO) {
            val updatedGame = currentGame.value.copy(status = status)
            gamesRepository.updateGameStatus(updatedGame)
                .onSuccess {
                    _detailsState.value = DetailsState.StatusUpdateSuccess
                    _currentGame.value = _currentGame.value.copy(status = status)
                }
                .onFailure {
                    _detailsState.value = DetailsState.StatusUpdateFailure
                }
                .onEmpty {
                    _detailsState.value = DetailsState.StatusUpdateFailure
                }
        }
    }

    private fun updateGameDetails() {
        _detailsState.value = DetailsState.DetailsUpdating
        viewModelScope.launch {
            gamesRepository.getGameDetails(currentGame.value)
                .onSuccess {
                    _currentGame.value = it
                    _detailsState.value = DetailsState.DetailsUpdateSuccess
                }
                .onFailure {
                    _detailsState.value = DetailsState.DetailsUpdateFailure
                }
                .onEmpty {
                    _detailsState.value = DetailsState.DetailsUpdateFailure
                }
        }
    }

    /**
     * Events
     */
    fun onDealsButtonClicked() {
        navigateToDeals()
    }

    /**
     * Navigation
     */
    private fun navigateToDeals() {
        navigationDispatcher.navigate(DetailsRouter.fromDetailsToDeals())
    }
}