package com.raudonikis.data_domain.game_status

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game.models.Game

object GameStatusUtils {

    /**
     * Updates the [GameStatus] for games with the same [id]
     */
    fun updateGameStatusForGames(
        outcome: Outcome<List<Game>>,
        id: Long,
        gameStatus: GameStatus
    ): Outcome<List<Game>> {
        return outcome.map { games ->
            games.map { game ->
                if (game.id == id) {
                    game.copy(status = gameStatus)
                } else {
                    game
                }
            }
        }
    }

    /**
     * Updates the [GameStatus] for games with the same [id]
     */
    fun updateGameStatusForActivities(
        outcome: Outcome<List<UserActivity>>,
        id: Long,
        gameStatus: GameStatus
    ): Outcome<List<UserActivity>> {
        return outcome.map { activities ->
            activities.map { activity ->
                if (activity.gameId == id && activity.action is UserActivityAction.ActionGameStatusUpdated) {
                    activity.copy(action = activity.action.copy(status = gameStatus))
                } else {
                    activity
                }
            }
        }
    }
}