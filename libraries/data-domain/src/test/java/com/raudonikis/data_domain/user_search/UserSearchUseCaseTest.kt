package com.raudonikis.data_domain.user_search

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.cache.GameDao
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_search.GameSearchUseCase
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.NetworkResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

@ExperimentalCoroutinesApi
class UserSearchUseCaseTest {

    private lateinit var userSearchUseCase: UserSearchUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var userDao: UserDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        userSearchUseCase = UserSearchUseCase(userDao, gamesApi, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `getUserSearchResults, returns from userDao`() {
        //Assemble
        val expectedResult = flowOf(Outcome.empty())
        every { userDao.getUserSearchResults() } returns expectedResult
        //Act
        val result = userSearchUseCase.getUserSearchResults()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `clearUserSearchResults, calls userDao with Outcome_Empty`() {
        //Assemble
        //Act
        userSearchUseCase.clearUserSearchResults()
        //Assert
        verify { userDao.setUserSearchResultsOutcome(Outcome.empty()) }
    }

    @Test
    fun `searchUsers, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.searchUsers(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = userSearchUseCase.searchUsers("test")
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `searchUsers, calls searchUsers from gamesApi`() =
        runBlockingTest {
            //Assemble
            val userName = "test"
            //Act
            userSearchUseCase.searchUsers(userName)
            //Assert
            coVerify { gamesApi.searchUsers(userName) }
        }

    @Test
    fun `searchUsers, sets Loading state, then sets Result state`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.searchUsers(any()) } returns NetworkResponse(
                isSuccess = true,
                data = listOf()
            )
            val expectedResult = Outcome.empty()
            //Act
            userSearchUseCase.searchUsers("test")
            //Assert
            verifySequence {
                userDao.setUserSearchResultsOutcome(Outcome.loading())
                userDao.setUserSearchResultsOutcome(expectedResult)
            }
        }
}