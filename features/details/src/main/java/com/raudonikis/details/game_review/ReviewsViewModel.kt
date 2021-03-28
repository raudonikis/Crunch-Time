package com.raudonikis.details.game_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_review.GameReview
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

    private var gameId: Long? = null
    private var reviews: List<GameReview> = listOf()
    private val _reviewState: MutableStateFlow<ReviewState> = MutableStateFlow(ReviewState.INITIAL)

    /**
     * Observables
     */
    val reviewState: Flow<ReviewState> = _reviewState

    /**
     * Initialisation
     */
    fun onCreate(gameId: Long, reviews: List<GameReview>) {
        this.gameId = gameId
        this.reviews = reviews
    }

    /**
     * Review functionality
     */
    fun postReview(rating: GameRating, comment: String) {
        gameId?.let { id ->
            val reviewBody = GameReviewRequestBody(
                content = comment,
                isPositive = rating == GameRating.UP_VOTED,
                gameId = id
            )
            _reviewState.value = ReviewState.LOADING
            viewModelScope.launch(Dispatchers.IO) {
                gamesRepository.postReview(reviewBody)
                    .onSuccess {
                        _reviewState.value = ReviewState.SUCCESS
                    }
                    .onFailure {
                        _reviewState.value = ReviewState.FAILURE
                    }
            }
        }
    }
}