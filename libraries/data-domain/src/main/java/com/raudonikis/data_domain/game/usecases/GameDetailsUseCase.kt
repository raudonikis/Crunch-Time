package com.raudonikis.data_domain.game.usecases

import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.NetworkResponse
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameDetailsUseCase @Inject constructor(private val gamesApi: GamesApi) {

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
}