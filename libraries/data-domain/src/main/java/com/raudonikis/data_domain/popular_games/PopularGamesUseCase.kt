package com.raudonikis.data_domain.popular_games

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PopularGamesUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val gamesApi: GamesApi,
) {

    fun getPopularGames(): Flow<Outcome<List<Game>>> = gameDao.getPopularGames()

    /**
     * Fetch a list of currently popular games and update the database
     * @return [Outcome] with information about the operation success
     */
    suspend fun updatePopularGames(): Outcome<List<Game>> {
        gameDao.setPopularGamesOutcome(Outcome.loading())
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getPopularGames()
                    .map { GameMapper.fromPopularGameResponseList(it) }
            }
        }.toOutcome().also { outcome ->
            gameDao.setPopularGamesOutcome(outcome)
            return outcome
        }
    }
}