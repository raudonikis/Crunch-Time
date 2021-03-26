package com.raudonikis.network.auth.login

import com.raudonikis.network.auth.UserResponse
import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "user")
    val userResponse: UserResponse,
    @Json(name = "accessToken")
    val accessToken: String,
)