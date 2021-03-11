package com.raudonikis.network.game

import com.squareup.moshi.Json

data class GameStatusResponse(
    @Json(name = "status")
    val status: String
)

/**
 * "uuid": "92ecf83b-3b09-491c-8416-96d371277eb5",
"game_id": 5,
"user_id": "00d71fe1-7cd2-48c4-9333-49d6a8323fe4",
"status": "played",
"created_at": "2021-03-11T12:12:25.000000Z",
"updated_at": "2021-03-11T12:12:42.000000Z"
 */
