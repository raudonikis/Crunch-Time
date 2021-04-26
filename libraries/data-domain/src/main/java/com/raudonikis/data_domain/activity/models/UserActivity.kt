package com.raudonikis.data_domain.activity.models

import java.util.*

data class UserActivity(
    val gameId: Long,
    val action: UserActivityAction,
    val coverUrl: String? = null,
    val createdAt: Date?,
)