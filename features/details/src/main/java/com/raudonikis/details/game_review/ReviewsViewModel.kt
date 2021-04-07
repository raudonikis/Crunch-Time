package com.raudonikis.details.game_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_review.mappers.GameReviewMapper
import com.raudonikis.data_domain.game_review.usecases.GameReviewUseCase
import com.raudonikis.network.game_review.GameReviewRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    // Use cases
    private val gameReviewUseCase: GameReviewUseCase,
) : ViewModel() {

    private var _currentGame: MutableStateFlow<Game> = MutableStateFlow(Game())
    private val _writeReviewState: MutableStateFlow<ReviewState> =
        MutableStateFlow(ReviewState.INITIAL)

    /**
     * Observables
     */
    val writeReviewState: Flow<ReviewState> = _writeReviewState
    val currentGame: Flow<Game> = _currentGame

    /**
     * Initialisation
     */
    fun onCreate(game: Game) {
        _currentGame.value = game
    }

    /**
     * Review functionality
     */
    fun postReview(rating: GameRating, comment: String) {
        val game = _currentGame.value
        _writeReviewState.value = ReviewState.LOADING
        if (game.gameReviewInfo.isReviewPresent) {
            _writeReviewState.value = ReviewState.ALREADY_PRESENT
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            gameReviewUseCase.postReview(rating, comment, game)
                .onSuccess {
                    val gameReview =
                        GameReviewMapper.fromGameReviewPostResponse(it).addGameInfo(game)
                    _currentGame.value = GameMapper.addGameReview(game, gameReview)
                    _writeReviewState.value = ReviewState.SUCCESS
                }
                .onFailure {
                    _writeReviewState.value = ReviewState.FAILURE
                }
        }

    }
}