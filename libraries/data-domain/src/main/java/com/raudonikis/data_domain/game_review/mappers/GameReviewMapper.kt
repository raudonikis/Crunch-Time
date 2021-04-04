package com.raudonikis.data_domain.game_review.mappers

import com.raudonikis.common.date.DateFormatter
import com.raudonikis.data_domain.game_review.GameReview
import com.raudonikis.network.game_review.GameReviewPostResponse
import com.raudonikis.network.game_review.GameReviewResponse

object GameReviewMapper {

    /**
     * From [GameReviewResponse] to [GameReview]
     */
    fun fromGameReviewResponse(response: GameReviewResponse): GameReview {
        return GameReview(
            gameId = response.gameId,
            content = response.content,
            isPositive = response.isPositive == 1,
            createdAt = DateFormatter.stringToDate(response.createdAt),
            updatedAt = response.updatedAt,
            userId = response.userId,
        )
    }

    /**
     * From a list of [GameReviewResponse] to a list of [GameReview]
     */
    fun fromGameReviewResponseList(responses: List<GameReviewResponse>): List<GameReview> {
        return responses.map { fromGameReviewResponse(it) }
    }

    /**
     * From [GameReviewPostResponse] to [GameReview]
     */
    fun fromGameReviewPostResponse(response: GameReviewPostResponse): GameReview {
        return GameReview(
            gameId = response.gameId,
            content = response.content,
            isPositive = response.isPositive,
            createdAt = DateFormatter.stringToDate(response.createdAt),
            updatedAt = response.updatedAt,
            userId = response.userId
        )
    }

    /**
     * From a list of [GameReviewPostResponse] to a list of [GameReview]
     */
    fun fromGameReviewPostResponseList(responses: List<GameReviewPostResponse>): List<GameReview> {
        return responses.map { fromGameReviewPostResponse(it) }
    }
}