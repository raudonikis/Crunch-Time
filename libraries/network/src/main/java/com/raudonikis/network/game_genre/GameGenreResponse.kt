package com.raudonikis.network.game_genre

import com.squareup.moshi.Json

data class GameGenreResponse(
    @Json(name = "name")
    val name: String,
)
