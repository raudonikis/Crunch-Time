package com.raudonikis.network.activity

import com.squareup.moshi.Json

data class GameStatusUpdatedResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "game_name")
    val gameName: String,
    val coverUrl: String?
)

/**
"data": {
"status": "played",
"game_name": "Baldur's Gate"
},
 */