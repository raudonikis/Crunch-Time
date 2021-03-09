package com.raudonikis.network.game

import com.squareup.moshi.Json

data class GameStatusRequestBody(
    @Json(name = "status")
    val status: String
)