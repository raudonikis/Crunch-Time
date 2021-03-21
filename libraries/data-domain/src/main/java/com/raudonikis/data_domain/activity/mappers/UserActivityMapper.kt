package com.raudonikis.data_domain.activity.mappers

import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.network.activity.UserActivityResponse

object UserActivityMapper {

    /**
     * To [UserActivity] from [UserActivityResponse]
     */
    private fun fromUserActivityResponse(response: UserActivityResponse): UserActivity {
        when (response) {
            is UserActivityResponse.UserActivityGameStatusResponse -> {
                return UserActivity(
                    gameId = response.gameId,
                    action = UserActivityActionMapper.fromUserActivityDataResponse(response),
                    coverUrl = response.coverUrl,
                )
            }
            is UserActivityResponse.UserActivityGameRatedResponse -> {
                return UserActivity(
                    gameId = response.gameId,
                    action = UserActivityActionMapper.fromUserActivityDataResponse(response),
                    coverUrl = response.coverUrl
                )
            }
        }
    }

    /**
     * To List<[UserActivity]> from List<[UserActivityResponse]>
     */
    fun fromUserActivityResponseList(userActivityResponseList: List<UserActivityResponse>): List<UserActivity> {
        return userActivityResponseList.map { fromUserActivityResponse(it) }
    }
}