package com.raudonikis.network.activity

import com.squareup.moshi.Json

data class ActionGameRatedResponse(
    @Json(name = "positive")
    val isPositive: Boolean,
    @Json(name = "game_name")
    val gameTitle: String?,
    @Json(name = "user_name")
    val userName: String,
)