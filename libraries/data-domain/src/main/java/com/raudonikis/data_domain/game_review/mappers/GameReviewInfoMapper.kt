package com.raudonikis.data_domain.game_review.mappers

import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.network.game_review.GameReviewInfoResponse

object GameReviewInfoMapper {

    fun fromGameReviewInfoResponse(response: GameReviewInfoResponse): GameReviewInfo {
        return GameReviewInfo(
            positiveCount = response.positiveCount,
            negativeCount = response.negativeCount,
            reviews = GameReviewMapper.fromGameReviewResponseList(response.reviews)
        )
    }

    fun fromGameReviewInfoResponseList(responses: List<GameReviewInfoResponse>): List<GameReviewInfo> {
        return responses.map { fromGameReviewInfoResponse(it) }
    }
}