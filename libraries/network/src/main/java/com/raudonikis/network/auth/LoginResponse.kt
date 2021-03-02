package com.raudonikis.network.auth

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "user")
    val userResponse: UserResponse,
    @Json(name = "accessToken")
    val accessToken: String,
)