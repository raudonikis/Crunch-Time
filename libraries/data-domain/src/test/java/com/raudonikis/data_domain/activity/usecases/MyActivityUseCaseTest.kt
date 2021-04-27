package com.raudonikis.data_domain.activity.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.network.GamesApi
import com.raudonikis.network.activity.UserActivityResponse
import com.raudonikis.network.utils.NetworkResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MyActivityUseCaseTest {

    private lateinit var myActivityUseCase: MyActivityUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var userActivityDao: UserActivityDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        myActivityUseCase = MyActivityUseCase(userActivityDao, gamesApi, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() //reset
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `getMyUserActivity, returns from userActivityDao`() {
        //Assemble
        val emptyOutcome = Outcome.success(listOf<UserActivity>())
        every { userActivityDao.getMyUserActivity() } returns flowOf(emptyOutcome)
        //Act
        val result = myActivityUseCase.getMyUserActivity()
        //Assert
        assertEquals(userActivityDao.getMyUserActivity(), result)
    }

    @Test
    fun `getMyUserActivity, calls userActivityDao`() {
        //Assemble
        val emptyOutcome = Outcome.success(listOf<UserActivity>())
        every { userActivityDao.getMyUserActivity() } returns flowOf(emptyOutcome)
        //Act
        myActivityUseCase.getMyUserActivity()
        //Assert
        verify { userActivityDao.getMyUserActivity() }
    }

    @Test
    fun `updateMyUserActivity, success empty, updates DAO with empty`() = runBlockingTest {
        //Assemble
        val getUserActivityResponse =
            NetworkResponse<List<UserActivityResponse>>(isSuccess = true, data = null)
        coEvery { gamesApi.getUserActivity() } returns getUserActivityResponse
        val expectedResult = Outcome.empty()
        //Act
        myActivityUseCase.updateMyUserActivity()
        //Assert
        verify { userActivityDao.setMyUserActivityOutcome(expectedResult) }
    }

    @Test
    fun `updateMyUserActivity, success with data, updates DAO with success`() = runBlockingTest {
        //Assemble
        val getUserActivityResponse =
            NetworkResponse<List<UserActivityResponse>>(isSuccess = true, data = listOf())
        coEvery { gamesApi.getUserActivity() } returns getUserActivityResponse
        val expectedResult = Outcome.empty()
        //Act
        myActivityUseCase.updateMyUserActivity()
        //Assert
        verify { userActivityDao.setMyUserActivityOutcome(expectedResult) }
    }

    @Test
    fun `updateMyUserActivity, failure, updates DAO with failure`() = runBlockingTest {
        //Assemble
        val getUserActivityResponse =
            NetworkResponse<List<UserActivityResponse>>(isSuccess = false)
        coEvery { gamesApi.getUserActivity() } returns getUserActivityResponse
        val expectedResult = Outcome.failure()
        //Act
        myActivityUseCase.updateMyUserActivity()
        //Assert
        verify { userActivityDao.setMyUserActivityOutcome(expectedResult) }
    }

    @Test
    fun `updateMyUserActivity, when gamesApi throws exception, updates DAO with failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getUserActivity() } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            myActivityUseCase.updateMyUserActivity()
            //Assert
            verify { userActivityDao.setMyUserActivityOutcome(expectedResult) }
        }
}