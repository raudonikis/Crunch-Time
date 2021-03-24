package com.raudonikis.network.auth.register

import com.squareup.moshi.Json

data class RegisterRequestBody(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "password_confirmation")
    val passwordConfirmation: String,
    @Json(name = "name")
    val name: String,
)

/**
 * {
"email": "n.nevada@gmail.com",
"password": "password",
"password_confirmation": "password",
"name": "pipsas"
}
 */
