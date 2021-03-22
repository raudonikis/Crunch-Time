package com.raudonikis.network.deals

import com.squareup.moshi.Json

data class DealSearchResponse(
    @Json(name = "list")
    val deals: List<DealResponse>,
//    val urls
)

/**
 * "Red Dead Redemption 2": {
"list": [
{
"price_new": 35.37,
"price_old": 59.99,
"price_cut": 41,
"url": "http://www.dpbolvw.net/click-6305441-10912384?URL=https%3A%2F%2Fwww.greenmangaming.com%2Fgames%2Fred-dead-redemption-2-pc%2F",
"shop": {
"id": "greenmangaming",
"name": "GreenManGaming"
},
"drm": [
"rockstarsocialclub"
]
},
{
"price_new": 40.19,
"price_old": 59.99,
"price_cut": 33,
"url": "https://store.steampowered.com/app/1174180/",
"shop": {
"id": "steam",
"name": "Steam"
},
"drm": [
"Rockstar Social Club"
]
} */
