package com.raudonikis.network.activity

import com.squareup.moshi.Json

data class ActionListCreatedResponse(
    @Json(name = "list_name")
    val listName: String,
    @Json(name = "user_name")
    val user: String = "",
)