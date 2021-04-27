package com.raudonikis.data_domain.popular_games

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.network.GamesApi
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
class PopularGamesUseCaseTest {

    private lateinit var popularGamesUseCase: PopularGamesUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var gameDao: GameDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        popularGamesUseCase =
            PopularGamesUseCase(gameDao, gamesApi, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `getPopularGames, returns from gameDao`() {
        //Assemble
        val expectedResult = flowOf(Outcome.empty())
        every { gameDao.getPopularGames() } returns expectedResult
        //Act
        val result = popularGamesUseCase.getPopularGames()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updatePopularGames, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getPopularGames() } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = popularGamesUseCase.updatePopularGames()
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updatePopularGames, calls getPopularGames from gamesApi`() =
        runBlockingTest {
            //Assemble
            //Act
            popularGamesUseCase.updatePopularGames()
            //Assert
            coVerify { gamesApi.getPopularGames() }
        }

    @Test
    fun `updatePopularGames, sets Loading state, then sets Result state`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getPopularGames() } returns NetworkResponse(
                isSuccess = true,
                data = listOf())
            val expectedResult = Outcome.empty()
            //Act
            popularGamesUseCase.updatePopularGames()
            //Assert
            verifySequence {
                gameDao.setPopularGamesOutcome(Outcome.loading())
                gameDao.setPopularGamesOutcome(expectedResult)
            }
        }
}