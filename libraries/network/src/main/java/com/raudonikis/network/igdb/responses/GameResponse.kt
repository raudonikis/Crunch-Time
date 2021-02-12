package com.raudonikis.network.igdb.responses

import com.squareup.moshi.Json

data class GameResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
)
