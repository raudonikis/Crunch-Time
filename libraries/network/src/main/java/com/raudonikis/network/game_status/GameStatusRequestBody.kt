package com.raudonikis.network.game_status

import com.squareup.moshi.Json

data class GameStatusRequestBody(
    @Json(name = "status")
    val status: String
)