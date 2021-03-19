package com.raudonikis.network.game

import com.raudonikis.network.game_genre.GameGenreResponse
import com.raudonikis.network.game_screenshot.GameScreenshotResponse
import com.raudonikis.network.game_video.GameVideoResponse
import com.squareup.moshi.Json

data class GameRelationsResponse(
    @Json(name = "cover")
    val cover: GameCoverResponse,
    @Json(name = "screenshots")
    val screenshots: List<GameScreenshotResponse>,
    @Json(name = "videos")
    val videos: List<GameVideoResponse>,
    @Json(name = "genres")
    val genres: List<GameGenreResponse>,
)
