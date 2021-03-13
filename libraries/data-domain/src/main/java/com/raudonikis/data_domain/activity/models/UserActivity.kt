package com.raudonikis.data_domain.activity.models

import com.raudonikis.data_domain.game.models.GameStatus

data class UserActivity(
    val name: String,
    val gameId: Long,
    val status: GameStatus,
    val coverUrl: String?
)