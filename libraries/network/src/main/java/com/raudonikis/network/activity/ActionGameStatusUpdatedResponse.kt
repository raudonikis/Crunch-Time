package com.raudonikis.network.activity

import com.squareup.moshi.Json

data class ActionGameStatusUpdatedResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "game_name")
    val gameName: String,
)
