package com.raudonikis.network.game

import com.squareup.moshi.Json

data class GameResponse(
    @Json(name = "attributes")
    val attributes: GameAttributesResponse,
    @Json(name = "relations")
    val relations: GameRelationsResponse,
)