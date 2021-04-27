package com.raudonikis.data_domain.game_collection

import com.raudonikis.common.Outcome
import com.raudonikis.core.providers.di.IODispatcher
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameCollectionUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val gamesApi: GamesApi,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    fun getGameCollection(gameCollectionType: GameCollectionType): Flow<Outcome<List<Game>>> =
        gameDao.getGameCollection(gameCollectionType)

    suspend fun updateGameCollection(): Outcome<List<Game>> {
        gameDao.setGameCollectionOutcome(Outcome.loading())
        withContext(ioDispatcher) {
            safeNetworkResponse {
                gamesApi.getGameCollection()
                    .map {
                        GameMapper.fromGameResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            gameDao.setGameCollectionOutcome(outcome)
            return outcome
        }
    }
}