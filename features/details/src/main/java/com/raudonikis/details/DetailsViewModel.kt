package com.raudonikis.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.data_domain.games.repo.GamesRepository
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

    fun updateGameStatus(game: Game, status: GameStatus) {
        _detailsState.value = DetailsState.StatusUpdating
        viewModelScope.launch(Dispatchers.IO) {
            val updatedGame = game.copy(status = status)
            gamesRepository.updateGameStatus(updatedGame)
                .onSuccess {
                    _detailsState.value = DetailsState.StatusUpdateSuccess
                    _currentGame.value = updatedGame
                }
                .onFailure {
                    _detailsState.value = DetailsState.StatusUpdateFailure
                }
        }
    }

    fun updateGameDetails() {

    }

    /**
     * Initialisation
     */
    fun onCreate(game: Game) {
        _currentGame.value = game
        if (game.isUpdateNeeded) {
            updateGameDetails()
        }
    }
}