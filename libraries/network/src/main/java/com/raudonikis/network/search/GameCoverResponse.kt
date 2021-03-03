package com.raudonikis.network.search

import com.squareup.moshi.Json

data class GameCoverResponse(
    @Json(name = "url")
    val url: String,
)

/**
"cover": {
"height": 1200,
"id": 120613,
"url": "//images.igdb.com/igdb/image/upload/t_thumb/co2l2d.jpg",
"width": 900
},*/