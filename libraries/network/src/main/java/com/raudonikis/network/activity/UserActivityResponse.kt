package com.raudonikis.network.activity

import com.squareup.moshi.Json

data class UserActivityResponse(
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "game_id")
    val gameId: Long,
    @Json(name = "action")
    val action: String,
    @Json(name = "data")
    val data: GameStatusUpdatedResponse,
)

/**
 *
"uuid": "92ecf855-fcda-4f8a-8b30-383fddddc6c7",
"user_id": "00d71fe1-7cd2-48c4-9333-49d6a8323fe4",
"game_id": 5,
"action": "game_status_updated",
"data": {
"status": "played",
"game_name": "Baldur's Gate"
},
"created_at": "2021-03-11T12:12:43.000000Z",
"updated_at": "2021-03-11T12:12:43.000000Z"
 */
