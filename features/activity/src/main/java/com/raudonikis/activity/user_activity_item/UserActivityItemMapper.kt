package com.raudonikis.activity.user_activity_item

import com.raudonikis.data_domain.activity.models.UserActivity

object UserActivityItemMapper {

    /**
     * To [UserActivityItem] from [UserActivity]
     */
    private fun fromUserActivity(userActivity: UserActivity): UserActivityItem {
        return UserActivityItem(userActivity)
    }

    /**
     * To List<[UserActivityItem]> from List<[UserActivity]>
     */
    fun fromUserActivityList(userActivities: List<UserActivity>): List<UserActivityItem> {
        return userActivities.map { fromUserActivity(it) }
    }
}