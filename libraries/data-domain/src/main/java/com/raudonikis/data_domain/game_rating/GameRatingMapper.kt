package com.raudonikis.data_domain.game_rating

import com.raudonikis.network.activity.ActionGameRatedResponse
import com.raudonikis.network.game_review.GameReviewPostResponse

object GameRatingMapper {

    fun fromGameReviewPostResponse(gameReviewPostResponse: GameReviewPostResponse): GameRating {
        return when (gameReviewPostResponse.isPositive) {
            true -> GameRating.UP_VOTED
            else -> GameRating.DOWN_VOTED
        }
    }

    fun fromActionGameRatedResponse(from: ActionGameRatedResponse): GameRating {
        return if (from.isPositive) {
            GameRating.UP_VOTED
        } else {
            GameRating.DOWN_VOTED
        }
    }
}