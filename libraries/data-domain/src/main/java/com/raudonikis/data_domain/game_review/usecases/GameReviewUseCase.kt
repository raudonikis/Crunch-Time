package com.raudonikis.data_domain.game_review.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.game_review.mappers.GameReviewInfoMapper
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game_review.GameReviewPostResponse
import com.raudonikis.network.game_review.GameReviewRequestBody
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameReviewUseCase @Inject constructor(
    private val gamesApi: GamesApi,
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
                        GameReviewInfoMapper.fromGameReviewInfoResponseWithGameInfo(response, game)
                    }
            }
        }.toOutcome()
    }

    /**
     * Post a new review
     */
    suspend fun postReview(reviewRequestBody: GameReviewRequestBody): Outcome<GameReviewPostResponse> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.postReview(reviewRequestBody)
            }
        }.toOutcome()
    }
}