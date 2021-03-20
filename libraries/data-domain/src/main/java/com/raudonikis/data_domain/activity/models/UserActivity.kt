package com.raudonikis.data_domain.activity.models

data class UserActivity(
    val gameId: Long,
    val action: UserActivityAction
)