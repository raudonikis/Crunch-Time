package com.raudonikis.network.utils

import com.squareup.moshi.Json

data class NetworkErrorResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "message")
    val message: String
)
