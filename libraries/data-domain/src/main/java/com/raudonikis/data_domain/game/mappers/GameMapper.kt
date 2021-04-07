package com.raudonikis.data_domain.game.mappers

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_genre.mappers.GameGenreMapper
import com.raudonikis.data_domain.game_review.GameReview
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.game_screenshot.mappers.GameScreenshotMapper
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_video.mappers.GameVideoMapper
import com.raudonikis.network.game.GameResponse
import com.raudonikis.network.game_search.GameSearchResponse
import com.raudonikis.network.popular_games.PopularGameResponse

object GameMapper {

    /**
     * From [GameSearchResponse] to [Game]
     */
    private fun fromGameSearchResponse(response: GameSearchResponse): Game {
        return Game(
            id = response.id,
            name = response.name,
            description = response.summary,
            coverUrl = response.cover?.url,
            releaseDate = response.releaseDate,
            gameGenres = GameGenreMapper.fromGameGenreResponseList(response.genres),
            screenshots = GameScreenshotMapper.fromScreenshotResponseList(response.screenshots),
            status = GameStatus.fromString(response.gameStatus),
        )
    }

    /**
     * From [List<GameSearchResponse>] to [List<Game>]
     */
    fun fromGameSearchResponseList(responses: List<GameSearchResponse>): List<Game> {
        return responses.map { fromGameSearchResponse(it) }
    }

    /**
     * From [GameResponse] to [Game]
     */
    fun fromGameResponse(response: GameResponse): Game {
        return Game(
            id = response.attributes.id,
            name = response.attributes.name,
            description = response.attributes.summary,
            coverUrl = response.relations.cover.url,
            screenshots = GameScreenshotMapper.fromScreenshotResponseList(response.relations.screenshots),
            videos = GameVideoMapper.fromGameVideResponseList(response.relations.videos),
            gameGenres = GameGenreMapper.fromGameGenreResponseList(response.relations.genres),
            releaseDate = response.attributes.releaseDate ?: "",
            status = GameStatus.fromString(response.attributes.gameStatus),
            gameReviewInfo = GameReviewInfo(isReviewPresent = response.attributes.review != null)
        )
    }

    /**
     * From [PopularGameResponse] to [Game]
     */
    private fun fromPopularGameResponse(response: PopularGameResponse): Game {
        return Game(
            id = response.id,
            name = response.name,
            description = response.summary,
            coverUrl = response.cover?.url,
            screenshots = GameScreenshotMapper.fromScreenshotResponseList(response.screenshots),
            videos = GameVideoMapper.fromGameVideResponseList(response.videos),
            gameGenres = GameGenreMapper.fromGameGenreResponseList(response.genres),
            releaseDate = response.releaseDate ?: "",
            status = GameStatus.fromString(response.gameStatus),
        )
    }

    /**
     * From List<[PopularGameResponse]> to List<[Game]>
     */
    fun fromPopularGameResponseList(responses: List<PopularGameResponse>): List<Game> {
        return responses.map { fromPopularGameResponse(it) }
    }

    /**
     * Add a new [GameReview] to a [Game]
     */
    fun addGameReview(game: Game, gameReview: GameReview): Game {
        var positiveCount = game.gameReviewInfo.positiveCount
        var negativeCount = game.gameReviewInfo.negativeCount
        if (gameReview.isPositive) {
            positiveCount++
        } else {
            negativeCount++
        }
        return game.copy(
            gameReviewInfo = game.gameReviewInfo.copy(
                positiveCount = positiveCount,
                negativeCount = negativeCount,
                reviews = game.gameReviewInfo.reviews.plus(gameReview)
            )
        )
    }
}