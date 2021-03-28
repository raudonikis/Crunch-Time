package com.raudonikis.details.game_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_review.mappers.GameReviewMapper
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.network.game_review.GameReviewRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val gamesRepository: GamesRepository,
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
        val reviewBody = GameReviewRequestBody(
            content = comment,
            isPositive = rating == GameRating.UP_VOTED,
            gameId = game.id
        )
        _writeReviewState.value = ReviewState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            gamesRepository.postReview(reviewBody)
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