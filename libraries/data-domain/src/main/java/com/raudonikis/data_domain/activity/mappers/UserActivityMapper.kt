package com.raudonikis.data_domain.activity.mappers

import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.network.activity.UserActivityResponse

object UserActivityMapper {

    /**
     * To [UserActivity]
     */
    fun fromUserActivityResponse(userActivityResponse: UserActivityResponse): UserActivity {
        return UserActivity(
            name = userActivityResponse.data.gameName,
            gameId = userActivityResponse.gameId,
            status = GameStatus.fromString(userActivityResponse.data.status),
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg"
        )
    }

    fun fromUserActivityResponseList(userActivityResponseList: List<UserActivityResponse>): List<UserActivity> {
        return userActivityResponseList.map { fromUserActivityResponse(it) }
    }
}