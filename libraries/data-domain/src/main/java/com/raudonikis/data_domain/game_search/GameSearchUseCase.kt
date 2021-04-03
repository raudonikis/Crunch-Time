package com.raudonikis.data_domain.game_search

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.cache.daos.GameDao
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameSearchUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val gamesApi: GamesApi,
) {

    fun getGameSearchResults(): Flow<Outcome<List<Game>>> = gameDao.getGameSearchResults()

    /**
     * Search for a game with the specified [name]
     * @return a list of [Game]
     */
    suspend fun search(name: String): Outcome<List<Game>> {
        gameDao.setGameSearchResultsOutcome(Outcome.loading())
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.search(name)
                    .map {
                        GameMapper.fromGameSearchResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            gameDao.setGameSearchResultsOutcome(outcome)
            return outcome
        }
    }

    fun clearSearchResults() {
        gameDao.setGameSearchResultsOutcome(Outcome.empty())
    }
}