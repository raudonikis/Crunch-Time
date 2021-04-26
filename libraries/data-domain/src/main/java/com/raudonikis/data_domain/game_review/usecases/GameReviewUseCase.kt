package com.raudonikis.data_domain.game_review.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.activity.mappers.UserActivityMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.game_review.mappers.GameReviewInfoMapper
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game_review.GameReviewPostResponse
import com.raudonikis.network.game_review.GameReviewRequestBody
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameReviewUseCase @Inject constructor(
    private val gamesApi: GamesApi,
    private val userActivityDao: UserActivityDao,
    private val userPreferences: UserPreferences,
) {

    /**
     * Fetch all of the review info associated with this game
     * @return [GameReviewInfo]
     */
    suspend fun getGameReviewInfo(game: Game): Outcome<GameReviewInfo> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getGameReviewInfo(game.id)
                    .map { response ->
                        GameReviewInfoMapper.fromGameReviewInfoResponseWithGameInfo(response, game, userPreferences.currentUser)
                    }
            }
        }.toOutcome()
    }

    /**
     * Post a new review
     */
    suspend fun postReview(
        rating: GameRating,
        comment: String,
        game: Game
    ): Outcome<GameReviewPostResponse> {
        return withContext(Dispatchers.IO) {
            val reviewBody = GameReviewRequestBody(
                content = comment,
                isPositive = rating == GameRating.UP_VOTED,
                gameId = game.id
            )
            safeNetworkResponse {
                gamesApi.postReview(reviewBody)
            }
        }.toOutcome().onSuccess { gameReviewPostResponse ->
            val newMyActivity = UserActivityMapper.onGameReviewUpdate(
                gameReviewPostResponse,
                game
            )
            userActivityDao.addNewMyActivity(newMyActivity)
        }
    }
}