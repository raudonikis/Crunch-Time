package com.raudonikis.details.game_deal

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.data_domain.game_deal.usecases.GameDealsUseCase
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DealsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    // Use cases
    private val gameDealsUseCase: GameDealsUseCase,
) : ViewModel() {

    private val _currentGame: MutableStateFlow<Game> = MutableStateFlow(Game())
    private val _dealsState: MutableStateFlow<Outcome<List<GameDeal>>> =
        MutableStateFlow(Outcome.empty())
    private val _errorEvent: Channel<String> = Channel()

    /**
     * Observables
     */
    val dealsState: Flow<Outcome<List<GameDeal>>> = _dealsState
    val errorEvent: Flow<String> = _errorEvent.receiveAsFlow()
    val currentGame: Flow<Game> = _currentGame

    /**
     * Initialisation
     */
    fun onCreate(game: Game) {
        _currentGame.value = game
        updateDeals()
    }

    private fun updateDeals() {
        _dealsState.value = Outcome.loading()
        viewModelScope.launch(Dispatchers.IO) {
            gameDealsUseCase.searchGameDeals(_currentGame.value).also { outcome ->
                _dealsState.value = outcome
            }.onFailure { error ->
                error?.let { _errorEvent.offer(error) }
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