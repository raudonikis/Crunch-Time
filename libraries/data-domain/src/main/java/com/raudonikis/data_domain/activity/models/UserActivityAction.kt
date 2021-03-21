package com.raudonikis.data_domain.activity.models

import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_rating.GameRating

sealed class UserActivityAction {
    data class ActionGameStatusUpdated(
        val status: GameStatus,
        val title: String,
    ) : UserActivityAction()

    data class ActionGameRanked(
        val title: String,
        val rating: GameRating,
    ) : UserActivityAction()
}
