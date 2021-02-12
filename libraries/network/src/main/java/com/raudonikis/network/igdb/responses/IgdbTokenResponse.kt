package com.raudonikis.network.igdb.responses

import com.squareup.moshi.Json

data class IgdbTokenResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "expires_in")
    val expiresIn: Long,
    @Json(name = "token_type")
    val tokenType: String,
)
