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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    suspend fun updateAllGameCollections(): List<Outcome<List<Game>>> {
        gameDao.setGameCollectionOutcome(Outcome.loading())
        return withContext(ioDispatcher) {
            GameCollectionType.values().map { type ->
                async {
                    safeNetworkResponse {
                        gamesApi.getGameCollection(type.toString())
                            .map {
                                GameMapper.fromGameSearchResponseList(it)
                            }
                    }
                }
            }.awaitAll().map { it.toOutcome().also { gameDao.updateGameCollectionOutcome(it) } }
        }
    }

    fun deleteGameCollections() {
        gameDao.setGameCollectionOutcome(Outcome.empty())
    }
}