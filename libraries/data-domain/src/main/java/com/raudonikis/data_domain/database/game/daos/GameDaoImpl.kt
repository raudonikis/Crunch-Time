package com.raudonikis.data_domain.database.game.daos

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.network.game_status.GameStatusResponse
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameDaoImpl @Inject constructor() : GameDao {

    /**
     * Data
     */
    private var popularGames: MutableStateFlow<Outcome<List<Game>>> =
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
     * Game status
     */
    override suspend fun updateGameStatus(gameStatusResponse: GameStatusResponse) {
        popularGames.value = popularGames.value.map { games ->
            games.map { game ->
                if (game.id == gameStatusResponse.gameId) {
                    game.copy(status = GameStatus.fromString(gameStatusResponse.status))
                } else {
                    game
                }
            }
        }
    }
}