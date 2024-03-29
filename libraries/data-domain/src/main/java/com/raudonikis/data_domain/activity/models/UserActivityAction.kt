package com.raudonikis.data_domain.activity.models

import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_rating.GameRating

sealed class UserActivityAction {
    data class ActionGameStatusUpdated(
        val status: GameStatus,
        val title: String,
        val user: String,
    ) : UserActivityAction()

    data class ActionGameRated(
        val title: String,
        val rating: GameRating,
        val user: String,
    ) : UserActivityAction()

    data class ActionListCreated(
        val listName: String,
        val user: String,
    ) : UserActivityAction()
}
