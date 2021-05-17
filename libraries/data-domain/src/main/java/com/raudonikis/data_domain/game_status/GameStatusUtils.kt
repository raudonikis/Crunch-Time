package com.raudonikis.data_domain.game_status

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game.models.Game
import timber.log.Timber

object GameStatusUtils {

    /**
     * Updates the [GameStatus] for games with the same [id]
     */
    fun updateGameStatusForGames(
        outcome: Outcome<List<Game>>,
        updatedGame: Game,
    ): Outcome<List<Game>> {
        return outcome.map { games ->
            games.map { game ->
                if (game.id == updatedGame.id) {
                    game.copy(status = updatedGame.status)
                } else {
                    game
                }
            }
        }
    }

    /**
     * Updates the [GameStatus] for games with the same [id]
     */
    fun updateOrAddGameStatusForGames(
        outcome: Outcome<List<Game>>,
        updatedGame: Game,
    ): Outcome<List<Game>> {
        outcome.onSuccess { games ->
            val exists = games.any { it.id == updatedGame.id }
            Timber.d("game exists? -> $exists")
            return if (exists) {
                updateGameStatusForGames(outcome, updatedGame)
            } else {
                Outcome.success(games + updatedGame)
            }
        }
        return Outcome.success(listOf(updatedGame))
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