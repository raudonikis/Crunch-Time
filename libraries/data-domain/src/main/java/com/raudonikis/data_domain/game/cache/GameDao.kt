package com.raudonikis.data_domain.game.cache

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
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
    suspend fun updateGameStatus(game: Game)

    /**
     * Game collections
     */
    fun getGameCollection(gameCollectionType: GameCollectionType): Flow<Outcome<List<Game>>>
    fun setGameCollectionOutcome(outcome: Outcome<List<Game>>)
    fun updateGameCollectionOutcome(outcome: Outcome<List<Game>>)
    suspend fun updateGameCollection(games: List<Game>)
}