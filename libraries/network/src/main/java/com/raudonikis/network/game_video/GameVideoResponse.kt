package com.raudonikis.network.game_video

import com.squareup.moshi.Json

data class GameVideoResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)