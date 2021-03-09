package com.raudonikis.network.auth

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "uuid")
    val uuid: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "email")
    val email: String,
)
