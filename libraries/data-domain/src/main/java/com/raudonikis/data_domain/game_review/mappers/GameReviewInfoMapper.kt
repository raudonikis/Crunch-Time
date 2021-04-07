package com.raudonikis.data_domain.game_review.mappers

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.user.User
import com.raudonikis.network.game_review.GameReviewInfoResponse

object GameReviewInfoMapper {

    /**
     * From [GameReviewInfoResponse] to [GameReviewInfo]
     */
    private fun fromGameReviewInfoResponse(
        response: GameReviewInfoResponse,
        user: User?
    ): GameReviewInfo {
        return GameReviewInfo(
            positiveCount = response.positiveCount,
            negativeCount = response.negativeCount,
            reviews = GameReviewMapper.fromGameReviewResponseList(response.reviews),
            isReviewPresent = isReviewPresent(response, user)
        )
    }

    fun fromGameReviewInfoResponseWithGameInfo(
        response: GameReviewInfoResponse,
        game: Game,
        user: User?,
    ): GameReviewInfo {
        val gameReviewInfo = fromGameReviewInfoResponse(response, user)
        return gameReviewInfo.copy(
            reviews = gameReviewInfo.reviews.map { it.addGameInfo(game) }
        )
    }

    private fun isReviewPresent(response: GameReviewInfoResponse, user: User?): Boolean {
        return response.reviews.any { reviewResponse ->
            reviewResponse.user.uuid == user?.uuid
        }
    }
}