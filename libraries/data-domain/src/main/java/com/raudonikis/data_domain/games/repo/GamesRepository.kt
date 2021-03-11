package com.raudonikis.data_domain.games.repo

import com.raudonikis.data_domain.games.mappers.GameMapper
import com.raudonikis.data_domain.games.mappers.GameStatusMapper
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game.GameStatusResponse
import com.raudonikis.network.utils.NetworkResponse
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
            gamesApi.search(name)
                .map {
                    GameMapper.fromGameSearchResponseList(it)
                }
        }
    }

    suspend fun updateGameStatus(game: Game): NetworkResponse<GameStatusResponse> {
        return withContext(Dispatchers.IO) {
            val gameStatus = gameStatusMapper.toJson(game.status)
            gamesApi.updateGameStatus(game.id, gameStatus)
        }
    }

    suspend fun getGameCollection(gameStatus: GameStatus): NetworkResponse<List<Game>> {
        delay(500)
        return NetworkResponse(
            true, listOf(
                Game(
                    name = "Game 1",
                    description = "Game 1 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc5pqn.jpg"
                ),
                Game(
                    name = "Game 2",
                    description = "Game 2 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc5pqn.jpg"
                ),
                Game(
                    name = "Game 3",
                    description = "Game 3 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc5pqn.jpg"
                ),
                Game(
                    name = "Game 4",
                    description = "Game 4 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc5pqn.jpg"
                ),
                Game(
                    name = "Game 5",
                    description = "Game 5 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc5pqn.jpg"
                ),
                Game(
                    name = "Game 6",
                    description = "Game 6 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc5pqn.jpg"
                ),
            ), null
        )
    }
}