package com.raudonikis.data_domain.game.repo

import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.mappers.GameStatusMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.data_domain.testGames
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game_status.GameStatusResponse
import com.raudonikis.network.utils.NetworkResponse
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val gamesApi: GamesApi,
) {

    /**
     * Search for a game with the specified [name]
     * @return a list of [Game]
     */
    suspend fun search(name: String): NetworkResponse<List<Game>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.search(name)
                    .map {
                        GameMapper.fromGameSearchResponseList(it)
                    }
            }
        }
    }

    /**
     * Get the details for the specific [game]
     * @return [Game] with the same id and additional data
     */
    suspend fun getGameDetails(game: Game): NetworkResponse<Game> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getGame(game.id)
                    .map {
                        GameMapper.fromGameResponse(it)
                    }
            }
        }
    }

    /**
     * Update the [GameStatus] for the specified game
     */
    suspend fun updateGameStatus(game: Game): NetworkResponse<GameStatusResponse> {
        return withContext(Dispatchers.IO) {
            val gameStatus = GameStatusMapper.toGameStatusRequestBody(game.status)
            safeNetworkResponse {
                gamesApi.updateGameStatus(game.id, gameStatus)
            }
        }
    }

    /**
     * Fetch a list of currently popular games
     * @return a list of [Game]
     */
    suspend fun getPopularGames(): NetworkResponse<List<Game>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getPopularGames()
                    .map { GameMapper.fromPopularGameResponseList(it) }
            }
        }
    }

    suspend fun getGameCollection(gameStatus: GameStatus): NetworkResponse<List<Game>> {
        delay(1000)
        return NetworkResponse(true, testGames)
    }
}