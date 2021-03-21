package com.raudonikis.data_domain.database.game.daos

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameDaoImpl @Inject constructor() : GameDao {

    /**
     * Data
     */
    private val popularGames: MutableStateFlow<Outcome<List<Game>>> =
        MutableStateFlow(Outcome.empty())
    private val gameSearchResults: MutableStateFlow<Outcome<List<Game>>> =
        MutableStateFlow(Outcome.empty())

    /**
     * Popular games
     */
    override fun getPopularGames(): Flow<Outcome<List<Game>>> {
        return popularGames
    }

    override fun setPopularGamesOutcome(outcome: Outcome<List<Game>>) {
        popularGames.value = outcome
    }

    override suspend fun updatePopularGames(games: List<Game>) {
        popularGames.value = Outcome.success(games)
    }

    /**
     * Game search
     */
    override fun getGameSearchResults(): Flow<Outcome<List<Game>>> {
        return gameSearchResults
    }

    override fun setGameSearchResultsOutcome(outcome: Outcome<List<Game>>) {
        gameSearchResults.value = outcome
    }

    override suspend fun updateGameSearchResults(games: List<Game>) {
        gameSearchResults.value = Outcome.success(games)
    }

    /**
     * Game status
     */
    override suspend fun updateGameStatus(id: Long, gameStatus: GameStatus) {
        popularGames.value = mapGameStatus(popularGames.value, id, gameStatus)
        gameSearchResults.value = mapGameStatus(gameSearchResults.value, id, gameStatus)
    }

    /**
     * Updates the [GameStatus] for games with the same [id]
     */
    private fun mapGameStatus(
        outcome: Outcome<List<Game>>,
        id: Long,
        gameStatus: GameStatus
    ): Outcome<List<Game>> {
        return outcome.map { games ->
            games.map { game ->
                if (game.id == id) {
                    game.copy(status = gameStatus)
                } else {
                    game
                }
            }
        }
    }
}