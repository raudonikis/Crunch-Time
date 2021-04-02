package com.raudonikis.data_domain.game.cache.daos

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.GameStatusUtils
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
    private val gameCollectionPlayed: MutableStateFlow<Outcome<List<Game>>> =
        MutableStateFlow(Outcome.empty())
    private val gameCollectionPlaying: MutableStateFlow<Outcome<List<Game>>> =
        MutableStateFlow(Outcome.empty())
    private val gameCollectionWant: MutableStateFlow<Outcome<List<Game>>> =
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
        popularGames.value =
            GameStatusUtils.updateGameStatusForGames(popularGames.value, id, gameStatus)
        gameSearchResults.value =
            GameStatusUtils.updateGameStatusForGames(gameSearchResults.value, id, gameStatus)
    }

    override fun getGameCollection(gameCollectionType: GameCollectionType): Flow<Outcome<List<Game>>> {
        return chooseGameCollection(gameCollectionType)
    }

    override fun setGameCollectionOutcome(
        gameCollectionType: GameCollectionType,
        outcome: Outcome<List<Game>>
    ) {
        chooseGameCollection(gameCollectionType).value = outcome
    }

    override suspend fun updateGameCollection(
        gameCollectionType: GameCollectionType,
        games: List<Game>
    ) {
        chooseGameCollection(gameCollectionType).value = Outcome.success(games)
    }

    private fun chooseGameCollection(gameCollectionType: GameCollectionType): MutableStateFlow<Outcome<List<Game>>> {
        return when (gameCollectionType) {
            GameCollectionType.PLAYED -> {
                gameCollectionPlayed
            }
            GameCollectionType.PLAYING -> {
                gameCollectionPlaying
            }
            GameCollectionType.WANT -> {
                gameCollectionWant
            }
        }
    }
}