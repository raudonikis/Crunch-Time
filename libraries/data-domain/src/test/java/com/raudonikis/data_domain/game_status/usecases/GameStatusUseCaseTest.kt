package com.raudonikis.data_domain.game_status.usecases

import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_search.GameSearchUseCase
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.network.GamesApi
import com.raudonikis.network.game_status.GameStatusResponse
import com.raudonikis.network.utils.NetworkResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameStatusUseCaseTest {

    private lateinit var gameStatusUseCase: GameStatusUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var gameDao: GameDao

    @RelaxedMockK
    private lateinit var userPreferences: UserPreferences

    @RelaxedMockK
    private lateinit var userActivityDao: UserActivityDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        gameStatusUseCase =
            GameStatusUseCase(gameDao, gamesApi, userPreferences, userActivityDao, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `updateGameStatus, calls updateGameStatus from gamesApi`() = runBlockingTest {
        //Assemble
        //Act
        gameStatusUseCase.updateGameStatus(Game())
        //Assert
        coVerify { gamesApi.updateGameStatus(any(), any()) }
    }

    @Test
    fun `updateGameStatus, onSuccess, updates gameDao`() = runBlockingTest {
        //Assemble
        val gameStatusResponse = GameStatusResponse("", 1, "")
        val game = Game()
        coEvery { gamesApi.updateGameStatus(any(), any()) } returns NetworkResponse(
            isSuccess = true,
            data = gameStatusResponse
        )
        //Act
        gameStatusUseCase.updateGameStatus(game)
        //Assert
        coVerify { gameDao.updateGameStatus(game.id, game.status) }
    }

    @Test
    fun `updateGameStatus, not success, does not update gameDao`() = runBlockingTest {
        //Assemble
        val game = Game()
        coEvery { gamesApi.updateGameStatus(any(), any()) } returns NetworkResponse(
            isSuccess = false
        )
        //Act
        gameStatusUseCase.updateGameStatus(game)
        //Assert
        coVerify(exactly = 0) { gameDao.updateGameStatus(any(), any()) }
    }

    @Test
    fun `updateGameStatus, onSuccess, adds new activity`() = runBlockingTest {
        //Assemble
        val gameStatusResponse = GameStatusResponse("", 1, "")
        val game = Game()
        coEvery { gamesApi.updateGameStatus(any(), any()) } returns NetworkResponse(
            isSuccess = true,
            data = gameStatusResponse
        )
        //Act
        gameStatusUseCase.updateGameStatus(game)
        //Assert
        coVerify { userActivityDao.addNewMyActivity(any()) }
    }

    @Test
    fun `updateGameStatus, not success, does not add new activity`() = runBlockingTest {
        //Assemble
        val game = Game()
        coEvery { gamesApi.updateGameStatus(any(), any()) } returns NetworkResponse(
            isSuccess = false
        )
        //Act
        gameStatusUseCase.updateGameStatus(game)
        //Assert
        coVerify(exactly = 0) { userActivityDao.addNewMyActivity(any()) }
    }
}