package com.raudonikis.network.popular_games

import com.raudonikis.network.game.GameCoverResponse
import com.raudonikis.network.game_genre.GameGenreResponse
import com.raudonikis.network.game_screenshot.GameScreenshotResponse
import com.raudonikis.network.game_video.GameVideoResponse
import com.squareup.moshi.Json

data class PopularGameResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "summary")
    val summary: String = "",
    @Json(name = "game_status")
    val gameStatus: String,
    @Json(name = "first_release_date")
    val releaseDate: String?,
    @Json(name = "cover")
    val cover: GameCoverResponse?,
    @Json(name = "screenshots")
    val screenshots: List<GameScreenshotResponse> = listOf(),
    @Json(name = "videos")
    val videos: List<GameVideoResponse> = listOf(),
    @Json(name = "genres")
    val genres: List<GameGenreResponse> = listOf(),
)

/**
 * {
"category": 0,
"cover": {
"id": 134193,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2vjl.jpg"
},
"first_release_date": "2021-04-05T00:00:00.000000Z",
"game_engines": [
603
],
"game_status": "empty",
"genres": [
{
"id": 33,
"name": "Arcade"
}
],
"id": 144816,
"involved_companies": [
122044
],
"name": "Sea Battle: Annihilation",
"platforms": [
{
"abbreviation": "PC",
"id": 6,
"name": "PC (Microsoft Windows)"
}
],
"screenshots": [
{
"id": 427116,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc95kc.jpg"
},
{
"id": 427117,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc95kd.jpg"
},
{
"id": 427118,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc95ke.jpg"
}
],
"summary": "Old style destroyer game, in which you destroy all the subs and other threats from bellow. \n \nTake control of a small battleship and with limited ammo fight against submarines coming your way. To defeat submarines, you will be able to use various weapons and powerups which will make your battleship true force on the sea.",
"videos": [
{
"id": 46818,
"name": "Trailer",
"url": "https://www.youtube.com/watch?v=KyZ8DhHo8fs",
"video_id": "KyZ8DhHo8fs"
}
]
},
 */
