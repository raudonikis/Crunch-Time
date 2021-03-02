package com.raudonikis.data_domain.mappers

import com.raudonikis.data_domain.models.GameModel
import com.raudonikis.network.api.responses.GameResponse

object GameModelMapper {

    fun fromGameResponse(gameResponse: GameResponse): GameModel {
        return GameModel(
            id = gameResponse.id,
            name = gameResponse.name,
        )
    }

    fun fromGameResponseList(gameResponseList: List<GameResponse>): List<GameModel> {
        return gameResponseList.map { fromGameResponse(it) }
    }
}