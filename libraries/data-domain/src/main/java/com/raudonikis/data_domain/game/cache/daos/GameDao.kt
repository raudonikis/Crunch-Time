package com.raudonikis.data_domain.game.cache.daos

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_status.GameStatus
import kotlinx.coroutines.flow.Flow

interface GameDao {

    /**
     * Popular games
     */
    fun getPopularGames(): Flow<Outcome<List<Game>>>
    fun setPopularGamesOutcome(outcome: Outcome<List<Game>>)
    suspend fun updatePopularGames(games: List<Game>)

    /**
     * Game search
     */
    fun getGameSearchResults(): Flow<Outcome<List<Game>>>
    fun setGameSearchResultsOutcome(outcome: Outcome<List<Game>>)
    suspend fun updateGameSearchResults(games: List<Game>)

    /**
     * Game status
     */
    suspend fun updateGameStatus(id: Long, gameStatus: GameStatus)
}