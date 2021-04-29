package com.raudonikis.details

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.usecases.GameDetailsUseCase
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.data_domain.game_deal.usecases.GameDealsUseCase
import com.raudonikis.data_domain.game_review.usecases.GameReviewUseCase
import com.raudonikis.data_domain.game_screenshot.GameScreenshot
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.usecases.GameStatusUseCase
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    // Use cases
    private val gameStatusUseCase: GameStatusUseCase,
    private val gameReviewUseCase: GameReviewUseCase,
    private val gameDetailsUseCase: GameDetailsUseCase,
    private val gameDealsUseCase: GameDealsUseCase,
) : ViewModel() {

    /**
     * States
     */
    private val _detailsState: MutableSharedFlow<DetailsState> =
        MutableSharedFlow()
    private val _dealsState: MutableStateFlow<Outcome<List<GameDeal>>> =
        MutableStateFlow(Outcome.empty())
    private val _currentGame: MutableStateFlow<Game> = MutableStateFlow(Game())
    private val _errorEvent: Channel<String> = Channel()

    /**
     * Observables
     */
    val detailsState: Flow<DetailsState> = _detailsState
    val currentGame: StateFlow<Game> = _currentGame
    val dealsState: Flow<Outcome<List<GameDeal>>> = _dealsState
    val errorEvent: Flow<String> = _errorEvent.receiveAsFlow()

    /**
     * Initialisation
     */
    fun onCreate(game: Game) {
        onGameUpdated(game)
        updateGameReviewInfo()
        if (game.isUpdateNeeded) {
            updateGameDetails()
        }
    }

    /**
     * Details functionality
     */
    fun updateGameStatus(status: GameStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            if (status == _currentGame.value.status) {
                _detailsState.emit(DetailsState.StatusUpdateNotNeeded)
                return@launch
            }
            _detailsState.emit(DetailsState.StatusUpdating)
            val updatedGame = _currentGame.value.copy(status = status)
            gameStatusUseCase.updateGameStatus(updatedGame)
                .onSuccess {
                    onGameUpdated(_currentGame.value.copy(status = status))
                    _detailsState.emit(DetailsState.StatusUpdateSuccess)
                }
                .onFailure {
                    _detailsState.emit(DetailsState.StatusUpdateFailure)
                }
                .onEmpty {
                    _detailsState.emit(DetailsState.StatusUpdateFailure)
                }
        }
    }

    private fun updateGameDetails() {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.DetailsUpdating)
            gameDetailsUseCase.getGameDetails(_currentGame.value)
                .onSuccess {
                    onGameUpdated(it.copy(gameReviewInfo = _currentGame.value.gameReviewInfo))
                    _detailsState.emit(DetailsState.DetailsUpdateSuccess)
                }
                .onFailure {
                    _detailsState.emit(DetailsState.DetailsUpdateFailure)
                }
                .onEmpty {
                    _detailsState.emit(DetailsState.DetailsUpdateFailure)
                }
        }
    }

    private fun updateGameReviewInfo() {
        viewModelScope.launch {
            gameReviewUseCase.getGameReviewInfo(_currentGame.value)
                .onSuccess {
                    onGameUpdated(_currentGame.value.copy(gameReviewInfo = it))
                }
                .onFailure {
                    Timber.w("Game review info failure")
                }
                .onEmpty {
                    Timber.w("Game review info failure")
                }
        }
    }

    fun getCurrentGameStatus(): GameStatus {
        return _currentGame.value.status
    }

    /**
     * Screenshot
     */
    fun getScreenshotAtPosition(position: Int): GameScreenshot? {
        return _currentGame.value.screenshots.getOrNull(position)
    }

    /**
     * Deals
     */
    private fun updateDeals() {
        _dealsState.value = Outcome.loading()
        viewModelScope.launch {
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
    fun onDealsButtonClicked() {
        updateDeals()
        navigateToDeals()
    }

    fun onReviewsButtonClicked() {
        navigateToReviews()
    }

    fun onGameUpdated(game: Game) {
        _currentGame.value = game
    }

    fun onGameStatusDeleteClicked() {
        updateGameStatus(GameStatus.EMPTY)
    }

    fun onScreenshotClicked(screenshotPosition: Int) {
        navigateToScreenshot(screenshotPosition)
    }

    fun onDealClicked(deal: GameDeal) {
        deal.url?.let { url ->
            navigateToDealUri(url)
        }
    }

    /**
     * Navigation
     */
    private fun navigateToDeals() {
        navigationDispatcher.navigate(DetailsRouter.fromDetailsToDeals())
    }

    private fun navigateToReviews() {
        navigationDispatcher.navigate(DetailsRouter.fromDetailsToReviews(_currentGame.value))
    }

    private fun navigateToScreenshot(screenshotPosition: Int) {
        navigationDispatcher.navigate(DetailsRouter.fromDetailsToScreenshot(screenshotPosition))
    }

    private fun navigateToDealUri(dealUrl: String) {
        navigationDispatcher.navigate(dealUrl)
    }
}