package com.raudonikis.network.game

import com.squareup.moshi.Json

data class GameScreenshotResponse(
    @Json(name = "url")
    val url: String
)
/**
 * "screenshots": [
{
"id": 418563,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc8yyr.jpg"
},
 */