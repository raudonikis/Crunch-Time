package com.raudonikis.details.game_deal

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DealsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
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

    /**
     * Events
     */
    fun onDealClicked(deal: GameDeal) {
        deal.url?.let { url ->
            navigateToDealUri(url.toUri())
        }
    }

    /**
     * Navigation
     */
    private fun navigateToDealUri(dealUri: Uri) {
        navigationDispatcher.navigate(dealUri)
    }
}