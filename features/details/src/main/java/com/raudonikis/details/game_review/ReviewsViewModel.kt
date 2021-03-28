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
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    private var gameId: Long? = null
    private var reviews: List<GameReview> = listOf()

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
            viewModelScope.launch(Dispatchers.IO) {
                gamesRepository.postReview(reviewBody)
                    .onSuccess {
                        Timber.d("post review -> Success")
                    }
                    .onFailure {
                        Timber.d("post review -> Failure")
                    }
            }
        }
    }
}