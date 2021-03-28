package com.raudonikis.details.game_deal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.repo.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealsViewModel @Inject constructor(
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    private val _currentGame: MutableStateFlow<Game> = MutableStateFlow(Game())
    private val _dealsState: MutableStateFlow<DealsState> = MutableStateFlow(DealsState.Initial)

    /**
     * Observables
     */
    val dealsState: Flow<DealsState> = _dealsState
    val currentGame: Flow<Game> = _currentGame

    /**
     * Initialisation
     */
    fun onCreate(game: Game) {
        _currentGame.value = game
        updateDeals()
    }

    private fun updateDeals() {
        _dealsState.value = DealsState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            gamesRepository.searchGameDeals(_currentGame.value)
                .onSuccess {
                    _dealsState.value = DealsState.Success(it)
                }
                .onFailure {
                    _dealsState.value = DealsState.Failure
                }
        }
    }
}