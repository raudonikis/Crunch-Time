package com.raudonikis.data_domain.game_search

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game.GameResponse
import com.raudonikis.network.game_search.GameSearchResponse
import com.raudonikis.network.utils.NetworkResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameSearchUseCaseTest {

    private lateinit var gameSearchUseCase: GameSearchUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var gameDao: GameDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        gameSearchUseCase = GameSearchUseCase(gameDao, gamesApi, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `getGameSearchResults, returns from gameDao`() {
        //Assemble
        val expectedResult = flowOf(Outcome.success(listOf<Game>()))
        every { gameDao.getGameSearchResults() } returns expectedResult
        //Act
        val result = gameSearchUseCase.getGameSearchResults()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `clearSearchResults, calls gameDao with Outcome_Empty`() {
        //Assemble
        //Act
        gameSearchUseCase.clearSearchResults()
        //Assert
        verify { gameDao.setGameSearchResultsOutcome(Outcome.empty()) }
    }

    @Test
    fun `search, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            coEvery { gamesApi.search(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = gameSearchUseCase.search(game.name)
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `search, calls search from gamesApi`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            //Act
            gameSearchUseCase.search(game.name)
            //Assert
            coVerify { gamesApi.search(game.name) }
        }

    @Test
    fun `search, sets Loading state, then sets Result state`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.search(any()) } returns NetworkResponse(
                isSuccess = true,
                data = listOf()
            )
            val expectedResult = Outcome.empty()
            //Act
            gameSearchUseCase.search("test-game")
            //Assert
            verifySequence {
                gameDao.setGameSearchResultsOutcome(Outcome.loading())
                gameDao.setGameSearchResultsOutcome(expectedResult)
            }
        }
}