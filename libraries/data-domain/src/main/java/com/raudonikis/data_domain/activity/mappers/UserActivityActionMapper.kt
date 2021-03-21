package com.raudonikis.data_domain.activity.mappers

import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.network.activity.UserActivityResponse

object UserActivityActionMapper {

    /**
     * To [UserActivityAction] from [UserActivityResponse]
     */
    fun fromUserActivityDataResponse(response: UserActivityResponse): UserActivityAction {
        return when (response) {
            is UserActivityResponse.UserActivityGameStatusResponse -> {
                UserActivityAction.ActionGameStatusUpdated(
                    status = GameStatus.fromString(response.data.status),
                    title = response.data.gameName,
                )
            }
            is UserActivityResponse.UserActivityGameRatedResponse -> {
                UserActivityAction.ActionGameRated(
                    title = response.data.name,
                    rating = GameRating.UP_VOTED
                )
            }
        }
    }
}