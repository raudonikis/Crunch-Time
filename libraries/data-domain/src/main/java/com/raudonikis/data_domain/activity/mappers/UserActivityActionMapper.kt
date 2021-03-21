package com.raudonikis.data_domain.activity.mappers

import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.network.activity.UserActivityDataResponse

object UserActivityActionMapper {

    /**
     * To [UserActivityAction] from [UserActivityDataResponse]
     */
    fun fromUserActivityDataResponse(response: UserActivityDataResponse): UserActivityAction {
        return when (response) {
            is UserActivityDataResponse.ActionGameStatusUpdatedResponse -> {
                UserActivityAction.ActionGameStatusUpdated(
                    status = GameStatus.fromString(response.status),
                    title = response.gameName,
                )
            }
            is UserActivityDataResponse.ActionGameRankedResponse -> {
                UserActivityAction.ActionGameRanked(
                    title = response.name,
                    rating = GameRating.UP_VOTED
                )
            }
        }
    }
}