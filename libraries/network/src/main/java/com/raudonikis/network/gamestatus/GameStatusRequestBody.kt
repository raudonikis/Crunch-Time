package com.raudonikis.network.gamestatus

import com.squareup.moshi.Json

data class GameStatusRequestBody(
    @Json(name = "status")
    val status: String
)