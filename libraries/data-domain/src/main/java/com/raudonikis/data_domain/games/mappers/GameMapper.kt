package com.raudonikis.data_domain.games.mappers

import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
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
            status = GameStatus.fromString(gameSearchResponse.gameStatus)
        )
    }

    fun fromGameSearchResponseList(gameSearchResponseList: List<GameSearchResponse>): List<Game> {
        return gameSearchResponseList.map { fromGameSearchResponse(it) }
    }
}