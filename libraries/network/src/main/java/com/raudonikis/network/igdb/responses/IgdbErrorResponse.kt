package com.raudonikis.network.igdb.responses

import com.squareup.moshi.Json

data class IgdbErrorResponse(
    @Json(name = "Message")
    val message: String
)
