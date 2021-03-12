package com.raudonikis.network.game

import com.squareup.moshi.Json

data class GameRelationsResponse(
    @Json(name = "cover")
    val cover: GameCoverResponse,
    @Json(name = "screenshots")
    val screenshots: List<GameScreenshotResponse>
)
