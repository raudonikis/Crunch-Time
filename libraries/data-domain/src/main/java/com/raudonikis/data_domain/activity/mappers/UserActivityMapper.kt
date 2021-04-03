package com.raudonikis.data_domain.activity.mappers

import com.raudonikis.common.date.DateFormatter
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.user.User
import com.raudonikis.network.activity.UserActivityResponse
import com.raudonikis.network.game_status.GameStatusResponse

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
                    createdAt = DateFormatter.stringToDate(response.createdAt),
                )
            }
            is UserActivityResponse.UserActivityGameRatedResponse -> {
                return UserActivity(
                    gameId = response.gameId,
                    action = UserActivityActionMapper.fromUserActivityDataResponse(response),
                    coverUrl = response.coverUrl,
                    createdAt = DateFormatter.stringToDate(response.createdAt),
                )
            }
            is UserActivityResponse.UserActivityListCreatedResponse -> {
                return UserActivity(
                    gameId = response.gameId ?: 0, //todo
                    action = UserActivityActionMapper.fromUserActivityDataResponse(response),
                    coverUrl = response.coverUrl,
                    createdAt = DateFormatter.stringToDate(response.createdAt),
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

    /**
     * From [GameStatusResponse],[Game] and [User] after a successful game status update to [UserActivity]
     */
    fun onGameStatusUpdate(
        gameStatusResponse: GameStatusResponse,
        game: Game,
        user: User?
    ): UserActivity {
        return UserActivity(
            gameId = gameStatusResponse.gameId,
            action = UserActivityActionMapper.onGameStatusUpdate(gameStatusResponse, game, user),
            coverUrl = game.coverUrl,
            createdAt = DateFormatter.stringToDate(gameStatusResponse.createdAt),
        )
    }
}