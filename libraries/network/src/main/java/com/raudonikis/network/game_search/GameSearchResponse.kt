package com.raudonikis.network.game_search

import com.raudonikis.network.game.GameCoverResponse
import com.raudonikis.network.game_genre.GameGenreResponse
import com.raudonikis.network.game_platform.GamePlatformResponse
import com.raudonikis.network.game_screenshot.GameScreenshotResponse
import com.squareup.moshi.Json

data class GameSearchResponse(
    /*@Json(name = "category")
    val category: Int,
    @Json(name = "collection")
    val collection: Int,*/
    @Json(name = "cover")
    val cover: GameCoverResponse?,
    @Json(name = "first_release_date")
    val releaseDate: String = "",
    @Json(name = "aggregated_rating")
    val rating: Double?,
    @Json(name = "screenshots")
    val screenshots: List<GameScreenshotResponse> = listOf(),
    @Json(name = "genres")
    val genres: List<GameGenreResponse> = listOf(),
    @Json(name = "platforms")
    val platforms: List<GamePlatformResponse> = listOf(),
    @Json(name = "id")
    val id: Long,
//    val involvedCompanies: List<InvolvedCompany>,
    @Json(name = "name")
    val name: String,
    @Json(name = "summary")
    val summary: String = "",
    @Json(name = "game_status")
    val gameStatus: String = "",
)