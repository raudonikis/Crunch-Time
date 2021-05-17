package com.raudonikis.data_domain.game_status.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.core.providers.di.IODispatcher
import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.activity.mappers.UserActivityMapper
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.mappers.GameStatusMapper
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game_status.GameStatusResponse
import com.raudonikis.network.utils.NetworkResponse
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameStatusUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val gamesApi: GamesApi,
    private val userPreferences: UserPreferences,
    private val userActivityDao: UserActivityDao,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    /**
     * Update the [GameStatus] for the specified game
     */
    suspend fun updateGameStatus(game: Game): Outcome<GameStatusResponse> {
        return withContext(ioDispatcher) {
            val gameStatus = GameStatusMapper.toGameStatusRequestBody(game.status)
            safeNetworkResponse {
                gamesApi.updateGameStatus(game.id, gameStatus)
            }
        }.toOutcome().also { outcome ->
            outcome.onSuccess { gameStatusResponse ->
                gameDao.updateGameStatus(game)
                val newMyActivity = UserActivityMapper.onGameStatusUpdate(
                    gameStatusResponse,
                    game,
                    userPreferences.currentUser
                )
                userActivityDao.addNewMyActivity(newMyActivity)
            }
        }
    }
}