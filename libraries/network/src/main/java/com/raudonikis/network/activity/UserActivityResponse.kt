package com.raudonikis.network.activity

import com.squareup.moshi.Json

sealed class UserActivityResponse {
    data class UserActivityGameStatusResponse(
        @Json(name = "user_id")
        val userId: String,
        @Json(name = "game_id")
        val gameId: Long,
        @Json(name = "action")
        val action: String,
        @Json(name = "data")
        val data: ActionGameStatusUpdatedResponse,
        @Json(name = "cover_url")
        val coverUrl: String? = null,
    ) : UserActivityResponse()

    data class UserActivityGameRatedResponse(
        @Json(name = "user_id")
        val userId: String,
        @Json(name = "game_id")
        val gameId: Long,
        @Json(name = "action")
        val action: String,
        @Json(name = "data")
        val data: ActionGameRatedResponse,
        @Json(name = "cover_url")
        val coverUrl: String? = null,
    ) : UserActivityResponse()

    companion object {
        const val LABEL_ACTION = "action"
        const val ACTION_GAME_STATUS_UPDATED = "game_status_updated"
        const val ACTION_GAME_RANKED = "???" //todo
    }
}

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
