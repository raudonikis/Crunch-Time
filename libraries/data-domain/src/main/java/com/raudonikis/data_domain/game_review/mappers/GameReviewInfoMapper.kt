package com.raudonikis.data_domain.game_review.mappers

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.network.game_review.GameReviewInfoResponse

object GameReviewInfoMapper {

    /**
     * From [GameReviewInfoResponse] to [GameReviewInfo]
     */
    fun fromGameReviewInfoResponse(response: GameReviewInfoResponse): GameReviewInfo {
        return GameReviewInfo(
            positiveCount = response.positiveCount,
            negativeCount = response.negativeCount,
            reviews = GameReviewMapper.fromGameReviewResponseList(response.reviews)
        )
    }

    /**
     * From a list of [GameReviewInfoResponse] to a list of [GameReviewInfo]
     */
    fun fromGameReviewInfoResponseList(responses: List<GameReviewInfoResponse>): List<GameReviewInfo> {
        return responses.map { fromGameReviewInfoResponse(it) }
    }

    fun fromGameReviewInfoResponseWithGameInfo(
        response: GameReviewInfoResponse,
        game: Game
    ): GameReviewInfo {
        val gameReviewInfo = fromGameReviewInfoResponse(response)
        return gameReviewInfo.copy(
            reviews = GameReviewMapper.addGameInfo(
                game,
                gameReviewInfo.reviews
            )
        )
    }
}