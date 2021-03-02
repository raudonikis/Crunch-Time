package com.raudonikis.network.api.responses

import com.squareup.moshi.Json

data class GameResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
)
