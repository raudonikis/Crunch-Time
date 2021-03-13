package com.raudonikis.data_domain.game.mappers

import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.data_domain.game_genre.mappers.GameGenreMapper
import com.raudonikis.data_domain.game_screenshot.mappers.ScreenshotMapper
import com.raudonikis.network.game.GameResponse
import com.raudonikis.network.game_search.GameSearchResponse

object GameMapper {

    /**
     * From [GameSearchResponse] to [Game]
     */
    private fun fromGameSearchResponse(gameSearchResponse: GameSearchResponse): Game {
        return Game(
            id = gameSearchResponse.id,
            name = gameSearchResponse.name,
            description = gameSearchResponse.summary ?: "",
            coverUrl = gameSearchResponse.cover?.url,
            screenshots = ScreenshotMapper.fromScreenshotResponseList(gameSearchResponse.screenshots),
            status = GameStatus.fromString(gameSearchResponse.gameStatus)
        )
    }

    /**
     * From [List<GameSearchResponse>] to [List<Game>]
     */
    fun fromGameSearchResponseList(gameSearchResponseList: List<GameSearchResponse>): List<Game> {
        return gameSearchResponseList.map { fromGameSearchResponse(it) }
    }

    /**
     * From [GameResponse] to [Game]
     */
    fun fromGameResponse(gameResponse: GameResponse): Game {
        return Game(
            id = gameResponse.attributes.id,
            name = gameResponse.attributes.name,
            description = gameResponse.attributes.summary,
            coverUrl = gameResponse.relations.cover.url,
            screenshots = ScreenshotMapper.fromScreenshotResponseList(gameResponse.relations.screenshots),
            gameGenres = GameGenreMapper.fromGameGenreResponseList(gameResponse.relations.genres),
            status = GameStatus.fromString(gameResponse.attributes.gameStatus),
        )
    }

    /**
     * From [UserActivity] to [Game]
     */
    fun fromUserActivity(userActivity: UserActivity): Game {
        return Game(
            id = userActivity.gameId,
            name = userActivity.name,
            status = userActivity.status,
            coverUrl = userActivity.coverUrl,
            isUpdateNeeded = true,
        )
    }
}