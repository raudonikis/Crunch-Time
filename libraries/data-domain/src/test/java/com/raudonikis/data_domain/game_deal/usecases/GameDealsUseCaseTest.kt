package com.raudonikis.data_domain.game_deal.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.network.GamesApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameDealsUseCaseTest {

    private lateinit var gameDealsUseCase: GameDealsUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        gameDealsUseCase = GameDealsUseCase(gamesApi, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `searchGameDeals, calls searchGameDeals from gamesApi`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            //Act
            gameDealsUseCase.searchGameDeals(game)
            //Assert
            coVerify { gamesApi.searchGameDeals(game.name) }
        }

    @Test
    fun `searchGameDeals, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.searchGameDeals(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = gameDealsUseCase.searchGameDeals(Game())
            //Assert
            Assert.assertEquals(expectedResult, result)
        }
}