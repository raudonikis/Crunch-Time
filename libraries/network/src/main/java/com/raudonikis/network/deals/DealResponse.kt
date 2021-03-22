package com.raudonikis.network.deals

import com.squareup.moshi.Json

data class DealResponse(
    @Json(name = "price_new")
    val newPrice: Double,
    @Json(name = "price_old")
    val oldPrice: Double,
    @Json(name = "price_cut")
    val priceCut: Double,
    @Json(name = "shop")
    val shop: DealShopResponse,
)

/**
{
"price_new": 88.19,
"price_old": 89.99,
"price_cut": 2,
"url": "https://www.dlgamer.com/eu/games/buy-red-dead-redemption-2-ultimate-54098?affil=3429886050",
"shop": {
"id": "dlgamer",
"name": "DLGamer"
},
"drm": [
"Rockstar Social Club"
]
},*/