package com.raudonikis.data_domain.games.mappers

import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.network.game.GameStatusRequestBody
import com.raudonikis.network.search.GameSearchResponse
import com.squareup.moshi.Moshi
import javax.inject.Inject

class GameMapper @Inject constructor(private val moshi: Moshi) {

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

    fun fromGameStatusToJson(gameStatus: GameStatus): String {
        val adapter = moshi.adapter(GameStatusRequestBody::class.java)
        val gameStatusRequestBody = GameStatusRequestBody(gameStatus.toString())
        return adapter.toJson(gameStatusRequestBody)
    }
}