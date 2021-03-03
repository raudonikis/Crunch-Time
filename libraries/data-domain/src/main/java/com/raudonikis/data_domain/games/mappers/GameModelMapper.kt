package com.raudonikis.data_domain.games.mappers

import com.raudonikis.data_domain.games.models.GameModel
import com.raudonikis.network.search.GameSearchResponse

object GameModelMapper {

    private fun fromGameSearchResponse(gameSearchResponse: GameSearchResponse): GameModel {
        return GameModel(
            id = gameSearchResponse.id,
            name = gameSearchResponse.name,
            description = gameSearchResponse.summary ?: "",
            coverUrl = gameSearchResponse.cover?.url
        )
    }

    fun fromGameSearchResponseList(gameSearchResponseList: List<GameSearchResponse>): List<GameModel> {
        return gameSearchResponseList.map { fromGameSearchResponse(it) }
    }
}