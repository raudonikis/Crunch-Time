package com.raudonikis.data_domain.game_collection

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game.GameResponse
import com.raudonikis.network.utils.NetworkResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameCollectionUseCaseTest {

    private lateinit var gameCollectionUseCase: GameCollectionUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var gameDao: GameDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        gameCollectionUseCase = GameCollectionUseCase(gameDao, gamesApi, testDispatcher)
    }

    @After
    fun teardown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getGameCollection, calls gameDao`() =
        runBlockingTest {
            //Assemble
            val gameCollectionType = GameCollectionType.WANT
            //Act
            gameCollectionUseCase.getGameCollection(gameCollectionType)
            //Assert
            verify { gameDao.getGameCollection(gameCollectionType) }
        }

    @Test
    fun `updateGameCollection, calls getGameCollection from gameApi`() =
        runBlockingTest {
            //Assemble
            //Act
            gameCollectionUseCase.updateGameCollection()
            //Assert
            coVerify { gamesApi.getGameCollection() }
        }

    @Test
    fun `updateGameCollection, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getGameCollection() } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = gameCollectionUseCase.updateGameCollection()
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updateGameCollection, sets Loading state, then sets Result state`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getGameCollection() } returns NetworkResponse(
                isSuccess = true,
                data = listOf()
            )
            val expectedResult = Outcome.empty()
            //Act
            gameCollectionUseCase.updateGameCollection()
            //Assert
            verifySequence {
                gameDao.setGameCollectionOutcome(Outcome.loading())
                gameDao.setGameCollectionOutcome(expectedResult)
            }
        }
}