package com.raudonikis.data_domain.game.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.network.GamesApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameDetailsUseCaseTest {

    private lateinit var gameDetailsUseCase: GameDetailsUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        gameDetailsUseCase = GameDetailsUseCase(gamesApi, testDispatcher)
    }

    @After
    fun teardown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getGameDetails, calls getGame from gameApi`() =
        runBlockingTest {
            //Assemble
            //Act
            gameDetailsUseCase.getGameDetails(Game())
            //Assert
            coVerify { gamesApi.getGame(any()) }
        }

    @Test
    fun `getGameDetails, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getGame(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = gameDetailsUseCase.getGameDetails(Game())
            //Assert
            assertEquals(expectedResult, result)
        }
}