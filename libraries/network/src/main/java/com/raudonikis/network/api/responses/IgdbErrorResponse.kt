package com.raudonikis.network.api.responses

import com.squareup.moshi.Json

data class IgdbErrorResponse(
    @Json(name = "Message")
    val message: String
)
