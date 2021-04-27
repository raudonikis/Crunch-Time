package com.raudonikis.data_domain.game.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.core.providers.di.IODispatcher
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameDetailsUseCase @Inject constructor(
    private val gamesApi: GamesApi,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    /**
     * Get the details for the specific [game]
     * @return [Game] with the same id and additional data
     */
    suspend fun getGameDetails(game: Game): Outcome<Game> {
        return withContext(ioDispatcher) {
            safeNetworkResponse {
                gamesApi.getGame(game.id)
                    .map {
                        GameMapper.fromGameResponse(it)
                    }
            }
        }.toOutcome()
    }
}