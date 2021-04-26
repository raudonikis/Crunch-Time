package com.raudonikis.network.user

import com.squareup.moshi.Json

data class UserSearchResponse(
    @Json(name = "uuid")
    val uuid: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "email")
    val email: String,
)