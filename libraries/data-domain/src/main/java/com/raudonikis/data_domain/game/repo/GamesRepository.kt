package com.raudonikis.data_domain.game.repo

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.game.cache.daos.GameDao
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.game_review.mappers.GameReviewInfoMapper
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.GameStatusMapper
import com.raudonikis.data_domain.testGames
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game_status.GameStatusResponse
import com.raudonikis.network.utils.NetworkResponse
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val gamesApi: GamesApi,
    private val gameDao: GameDao,
) {

    /**
     * Observables
     */
    fun getPopularGames(): Flow<Outcome<List<Game>>> = gameDao.getPopularGames()
    fun getGameSearchResults(): Flow<Outcome<List<Game>>> = gameDao.getGameSearchResults()

    /**
     * Search for a game with the specified [name]
     * @return a list of [Game]
     */
    suspend fun search(name: String): Outcome<List<Game>> {
        gameDao.setGameSearchResultsOutcome(Outcome.loading())
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.search(name)
                    .map {
                        GameMapper.fromGameSearchResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            gameDao.setGameSearchResultsOutcome(outcome)
            return outcome
        }
    }

    suspend fun getGameReviewInfo(game: Game): Outcome<GameReviewInfo> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getGameReviewInfo(game.id)
                    .map { response ->
                        GameReviewInfoMapper.fromGameReviewInfoResponseWithGameInfo(response, game)
                    }
            }
        }.toOutcome()
    }

    suspend fun getDeals() {
        gamesApi.searchGameDeals("Hollow knight").also {
            Timber.d("lol")
        }
    }

    fun clearSearchResults() {
        gameDao.setGameSearchResultsOutcome(Outcome.empty())
    }

    /**
     * Get the details for the specific [game]
     * @return [Game] with the same id and additional data
     */
    suspend fun getGameDetails(game: Game): NetworkResponse<Game> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getGame(game.id)
                    .map {
                        GameMapper.fromGameResponse(it)
                    }
            }
        }
    }

    /**
     * Update the [GameStatus] for the specified game
     */
    suspend fun updateGameStatus(game: Game): NetworkResponse<GameStatusResponse> {
        return withContext(Dispatchers.IO) {
            val gameStatus = GameStatusMapper.toGameStatusRequestBody(game.status)
            safeNetworkResponse {
                gamesApi.updateGameStatus(game.id, gameStatus)
                    .onSuccess {
                        gameDao.updateGameStatus(game.id, game.status)
                    }
            }
        }
    }

    /**
     * Fetch a list of currently popular games and update the database
     * @return [Outcome] with information about the operation success
     */
    suspend fun updatePopularGames(): Outcome<List<Game>> {
        gameDao.setPopularGamesOutcome(Outcome.loading())
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getPopularGames()
                    .map { GameMapper.fromPopularGameResponseList(it) }
            }
        }.toOutcome().also { outcome ->
            gameDao.setPopularGamesOutcome(outcome)
            return outcome
        }
    }

    suspend fun getGameCollection(gameStatus: GameStatus): NetworkResponse<List<Game>> {
        delay(1000)
        return NetworkResponse(true, testGames)
    }
}