package com.raudonikis.network.activity

import com.squareup.moshi.Json

sealed class UserActivityDataResponse {
    data class ActionGameStatusUpdatedResponse(
        @Json(name = "status")
        val status: String,
        @Json(name = "game_name")
        val gameName: String,
        val coverUrl: String?
    ) : UserActivityDataResponse()

    data class ActionGameRankedResponse(val name: String) : UserActivityDataResponse()

    companion object {
        const val LABEL_ACTION = "action"
        const val ACTION_GAME_STATUS_UPDATED = "game_status_updated"
        const val ACTION_GAME_RANKED = "???" //todo
    }
}
