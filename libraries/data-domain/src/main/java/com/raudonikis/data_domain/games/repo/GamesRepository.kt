package com.raudonikis.data_domain.games.repo

import com.raudonikis.data_domain.games.mappers.GameMapper
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game.GameStatusResponse
import com.raudonikis.network.utils.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val gamesApi: GamesApi,
    private val gameMapper: GameMapper
) {

    suspend fun search(name: String): NetworkResponse<List<Game>> {
        return withContext(Dispatchers.IO) {
            gamesApi.search(name)
                .map {
                    gameMapper.fromGameSearchResponseList(it)
                }
        }
    }

    suspend fun updateGameStatus(game: Game): NetworkResponse<GameStatusResponse> {
        return withContext(Dispatchers.IO) {
            val gameStatus = gameMapper.fromGameStatusToJson(game.status)
            gamesApi.updateGameStatus(game.id, gameStatus)
        }
    }
}