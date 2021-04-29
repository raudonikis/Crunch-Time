package com.raudonikis.data_domain.news_feed

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.cache.UserActivityDao
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
class NewsFeedUseCaseTest {

    private lateinit var newsFeedUseCase: NewsFeedUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var userActivityDao: UserActivityDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsFeedUseCase =
            NewsFeedUseCase(userActivityDao, gamesApi, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `getNewsFeed, returns from UserActivityDao`() {
        //Assemble
        val expectedResult = flowOf(Outcome.empty())
        every { userActivityDao.getNewsFeed() } returns expectedResult
        //Act
        val result = newsFeedUseCase.getNewsFeed()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updateNewsFeed, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getNewsFeed() } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = newsFeedUseCase.updateNewsFeed()
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updateNewsFeed, calls getNewsFeed from gamesApi`() =
        runBlockingTest {
            //Assemble
            //Act
            newsFeedUseCase.updateNewsFeed()
            //Assert
            coVerify { gamesApi.getNewsFeed() }
        }

    @Test
    fun `updateNewsFeed, sets Loading state, then sets Result state`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getNewsFeed() } returns NetworkResponse(
                isSuccess = true,
                data = listOf())
            val expectedResult = Outcome.empty()
            //Act
            newsFeedUseCase.updateNewsFeed()
            //Assert
            verifySequence {
                userActivityDao.setNewsFeedOutcome(Outcome.loading())
                userActivityDao.setNewsFeedOutcome(expectedResult)
            }
        }

}