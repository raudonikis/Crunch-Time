package com.raudonikis.data_domain.games.mappers

import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.network.game.GameResponse
import com.raudonikis.network.search.GameSearchResponse

object GameMapper {

    /**
     * To [Game]
     */
    private fun fromGameSearchResponse(gameSearchResponse: GameSearchResponse): Game {
        return Game(
            id = gameSearchResponse.id,
            name = gameSearchResponse.name,
            description = gameSearchResponse.summary ?: "",
            coverUrl = gameSearchResponse.cover?.url,
            screenshotUrls = gameSearchResponse.screenshots.map { it.url },
            status = GameStatus.fromString(gameSearchResponse.gameStatus)
        )
    }

    fun fromGameSearchResponseList(gameSearchResponseList: List<GameSearchResponse>): List<Game> {
        return gameSearchResponseList.map { fromGameSearchResponse(it) }
    }

    fun fromGameResponse(gameResponse: GameResponse): Game {
        return Game(
            id = gameResponse.attributes.id,
            name = gameResponse.attributes.name,
            description = gameResponse.attributes.summary,
            coverUrl = gameResponse.relations.cover.url,
            screenshotUrls = gameResponse.relations.screenshots.map { it.url },
            status = GameStatus.fromString(gameResponse.attributes.gameStatus),
        )
    }

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