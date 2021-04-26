package com.raudonikis.profile.activity

import com.raudonikis.data_domain.activity.models.UserActivity

sealed class ActivitiesState {
    object Initial : ActivitiesState()
    object Loading : ActivitiesState()
    data class Success(val activities: List<UserActivity>) : ActivitiesState()
    object Failure : ActivitiesState()
}