package com.raudonikis.data_domain.activity.mappers

import com.raudonikis.common.date.DateFormatter
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.mappers.UserMapper
import com.raudonikis.network.activity.UserActivityResponse
import com.raudonikis.network.game_review.GameReviewPostResponse
import com.raudonikis.network.game_review.GameReviewResponse
import com.raudonikis.network.game_status.GameStatusResponse
import java.util.*

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
     * From [GameReviewResponse] to [UserActivity] after a successful game status update
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
            createdAt = Date(System.currentTimeMillis()),
        )
    }

    /**
     * From [GameReviewPostResponse] to [UserActivity] after a successful game review post
     */
    fun onGameReviewUpdate(
        gameReviewPostResponse: GameReviewPostResponse,
        game: Game
    ): UserActivity {
        return UserActivity(
            gameId = gameReviewPostResponse.gameId,
            action = UserActivityActionMapper.onGameReviewUpdate(
                gameReviewPostResponse,
                game,
                UserMapper.fromUserResponse(gameReviewPostResponse.user),
            ),
            coverUrl = game.coverUrl,
            createdAt = DateFormatter.stringToDate(gameReviewPostResponse.createdAt),
        )
    }
}