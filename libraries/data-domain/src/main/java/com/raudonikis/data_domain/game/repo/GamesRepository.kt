package com.raudonikis.data_domain.game.repo

import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.mappers.GameStatusMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
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
    private val gameStatusMapper: GameStatusMapper,
) {

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

    suspend fun updateGameStatus(game: Game): NetworkResponse<GameStatusResponse> {
        return withContext(Dispatchers.IO) {
            val gameStatus = gameStatusMapper.toJson(game.status)
            safeNetworkResponse {
                gamesApi.updateGameStatus(game.id, gameStatus)
            }
        }
    }

    suspend fun getGameCollection(gameStatus: GameStatus): NetworkResponse<List<Game>> {
        delay(500)
        return NetworkResponse(
            true, listOf(
                Game(
                    name = "Game 1",
                    description = "Game 1 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg"
                ),
                Game(
                    name = "Game 2",
                    description = "Game 2 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg"
                ),
                Game(
                    name = "Game 3",
                    description = "Game 3 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg"
                ),
                Game(
                    name = "Game 4",
                    description = "Game 4 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg"
                ),
                Game(
                    name = "Game 5",
                    description = "Game 5 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg"
                ),
                Game(
                    name = "Game 6",
                    description = "Game 6 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg"
                ),
            ), null
        )
    }
}