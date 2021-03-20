package com.raudonikis.data_domain.activity.models

import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.data_domain.game_rating.GameRating

sealed class UserActivityAction {
    data class ActionGameStatusUpdated(
        val status: GameStatus,
        val title: String,
        val coverUrl: String? = null,
    ) : UserActivityAction()

    data class ActionGameRanked(
        val title: String,
        val rating: GameRating,
        val coverUrl: String? = null,
    ) : UserActivityAction()
}
