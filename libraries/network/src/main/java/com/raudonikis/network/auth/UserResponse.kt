package com.raudonikis.network.auth

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "uuid")
    val uuid: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "email")
    val email: String,
)

/**
 * {
"name": "pipsas",
"email": "n.nevada@gmail.com",
"uuid": "93078c76-b1e4-436b-8cef-13ebe171af93",
"updated_at": "2021-03-24T17:18:26.000000Z",
"created_at": "2021-03-24T17:18:26.000000Z"
},
 */
