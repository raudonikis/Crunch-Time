package com.raudonikis.data_domain.game_status.usecases

import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.mappers.GameStatusMapper
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game_status.GameStatusResponse
import com.raudonikis.network.utils.NetworkResponse
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameStatusUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val gamesApi: GamesApi,
) {

    /**
     * Update the [GameStatus] for the specified game
     */
    suspend fun updateGameStatus(game: Game): NetworkResponse<GameStatusResponse> {
        return withContext(Dispatchers.IO) {
            val gameStatus = GameStatusMapper.toGameStatusRequestBody(game.status)
            safeNetworkResponse {
                gamesApi.updateGameStatus(game.id, gameStatus)
                    .onSuccess {
                        gameDao.updateGameStatus(game.id, game.status)
                    }
            }
        }
    }
}