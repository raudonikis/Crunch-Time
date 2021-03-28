package com.raudonikis.data_domain.game_review.mappers

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_review.GameReview
import com.raudonikis.network.game_review.GameReviewResponse

object GameReviewMapper {

    fun fromGameReviewResponse(response: GameReviewResponse): GameReview {
        return GameReview(
            gameId = response.gameId,
            content = response.content,
            isPositive = response.isPositive == 1,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            userId = response.userId,
        )
    }

    fun fromGameReviewResponseList(responses: List<GameReviewResponse>): List<GameReview> {
        return responses.map { fromGameReviewResponse(it) }
    }

    fun addGameInfo(game: Game, gameReviews: List<GameReview>): List<GameReview> {
        return gameReviews.map {
            it.copy(
                gameTitle = game.name,
                gameCoverUrl = game.coverUrl
            )
        }
    }
}