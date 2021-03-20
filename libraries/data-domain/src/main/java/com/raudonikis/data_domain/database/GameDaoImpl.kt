package com.raudonikis.data_domain.database

import com.raudonikis.data_domain.database.daos.GameDao
import com.raudonikis.data_domain.game.models.Game
import kotlinx.coroutines.flow.Flow

class GameDaoImpl : GameDao {

    override fun getPopularGames(): Flow<List<Game>> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePopularGames(games: List<Game>) {
        TODO("Not yet implemented")
    }
}