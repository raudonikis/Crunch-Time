package com.raudonikis.data_domain.game_collection

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.data_domain.testGames
import com.raudonikis.network.GamesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameCollectionUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val gamesApi: GamesApi,
) {

    fun getGameCollection(gameCollectionType: GameCollectionType): Flow<Outcome<List<Game>>> =
        gameDao.getGameCollection(gameCollectionType)

    suspend fun updateGameCollection() {
        gameDao.setGameCollectionOutcome(Outcome.loading())
        withContext(Dispatchers.IO) {
            gameDao.setGameCollectionOutcome(Outcome.success(testGames))
        }
    }
}