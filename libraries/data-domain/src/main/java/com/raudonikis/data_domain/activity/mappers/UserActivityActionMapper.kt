package com.raudonikis.data_domain.activity.mappers

import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_rating.GameRatingMapper
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.user.User
import com.raudonikis.network.activity.UserActivityResponse
import com.raudonikis.network.game_review.GameReviewPostResponse
import com.raudonikis.network.game_status.GameStatusResponse

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
                    user = response.data.user,
                )
            }
            is UserActivityResponse.UserActivityGameRatedResponse -> {
                UserActivityAction.ActionGameRated(
                    title = response.data.gameTitle ?: "",
                    rating = GameRatingMapper.fromActionGameRatedResponse(response.data),
                    user = response.data.userName,
                )
            }
            is UserActivityResponse.UserActivityListCreatedResponse -> {
                UserActivityAction.ActionListCreated(
                    listName = response.data.listName,
                    user = response.data.user,
                )
            }
        }
    }

    /**
     * From [GameStatusResponse] to [UserActivityAction] on successful game status update
     */
    fun onGameStatusUpdate(
        gameStatusResponse: GameStatusResponse,
        game: Game,
        user: User?
    ): UserActivityAction {
        return UserActivityAction.ActionGameStatusUpdated(
            status = GameStatus.fromString(gameStatusResponse.status),
            title = game.name,
            user = user?.name ?: ""
        )
    }

    /**
     * From [GameReviewPostResponse] to [UserActivityAction] on successful game status update
     */
    fun onGameReviewUpdate(
        gameReviewPostResponse: GameReviewPostResponse,
        game: Game,
        user: User?
    ): UserActivityAction {
        return UserActivityAction.ActionGameRated(
            rating = GameRatingMapper.fromGameReviewPostResponse(gameReviewPostResponse),
            title = game.name,
            user = user?.name ?: ""
        )
    }
}