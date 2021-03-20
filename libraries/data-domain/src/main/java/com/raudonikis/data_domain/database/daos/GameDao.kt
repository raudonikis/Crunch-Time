package com.raudonikis.data_domain.database.daos

import com.raudonikis.data_domain.game.models.Game
import kotlinx.coroutines.flow.Flow

interface GameDao {

    /**
     * Popular games
     */
    fun getPopularGames(): Flow<List<Game>>
    suspend fun updatePopularGames(games: List<Game>)
}