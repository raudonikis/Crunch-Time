package com.raudonikis.network.game_deals

import com.squareup.moshi.Json

data class DealShopResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
)

/**
 * "shop": {
"id": "dlgamer",
"name": "DLGamer"
},
 */
