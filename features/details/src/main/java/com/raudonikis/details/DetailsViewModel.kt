package com.raudonikis.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.usecases.GameDetailsUseCase
import com.raudonikis.data_domain.game_review.usecases.GameReviewUseCase
import com.raudonikis.data_domain.game_screenshot.GameScreenshot
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.usecases.GameStatusUseCase
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        if (status == currentGame.value.status) {
            _detailsState.value = DetailsState.StatusUpdateNotNeeded
            return
        }
        _detailsState.value = DetailsState.StatusUpdating
        viewModelScope.launch(Dispatchers.IO) {
            val updatedGame = currentGame.value.copy(status = status)
            gameStatusUseCase.updateGameStatus(updatedGame)
                .onSuccess {
                    onGameUpdated(_currentGame.value.copy(status = status))
                    _detailsState.value = DetailsState.StatusUpdateSuccess
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
            gameDetailsUseCase.getGameDetails(currentGame.value)
                .onSuccess {
                    onGameUpdated(it.copy(gameReviewInfo = currentGame.value.gameReviewInfo))
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

    private fun updateGameReviewInfo() {
        viewModelScope.launch(Dispatchers.IO) {
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

    /**
     * Screenshot
     */
    fun getScreenshotAtPosition(position: Int): GameScreenshot? {
        return _currentGame.value.screenshots.getOrNull(position)
    }

    /**
     * Events
     */
    fun onDealsButtonClicked() {
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

    /**
     * Navigation
     */
    private fun navigateToDeals() {
        navigationDispatcher.navigate(DetailsRouter.fromDetailsToDeals(_currentGame.value))
    }

    private fun navigateToReviews() {
        navigationDispatcher.navigate(
            DetailsRouter.fromDetailsToReviews(_currentGame.value)
        )
    }

    private fun navigateToScreenshot(screenshotPosition: Int) {
        navigationDispatcher.navigate(DetailsRouter.fromDetailsToScreenshot(screenshotPosition))
    }
}