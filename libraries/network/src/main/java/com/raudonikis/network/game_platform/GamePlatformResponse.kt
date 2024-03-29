package com.raudonikis.network.game_platform

import com.squareup.moshi.Json

data class GamePlatformResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: Long,
)
