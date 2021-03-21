package com.raudonikis.data_domain.database.game.daos

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.network.game_status.GameStatusResponse
import kotlinx.coroutines.flow.Flow

interface GameDao {

    /**
     * Popular games
     */
    fun getPopularGames(): Flow<Outcome<List<Game>>>
    fun setPopularGamesOutcome(outcome: Outcome<List<Game>>)
    suspend fun updatePopularGames(games: List<Game>)

    /**
     * Game status
     */
    suspend fun updateGameStatus(gameStatusResponse: GameStatusResponse)

}