package com.raudonikis.network.game_deals

import com.squareup.moshi.Json

data class DealSearchResponse(
    @Json(name = "list")
    val deals: List<DealResponse>,
    @Json(name = "name")
    val name: String,
//    val urls
)