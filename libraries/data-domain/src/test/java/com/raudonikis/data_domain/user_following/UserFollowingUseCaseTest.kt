package com.raudonikis.data_domain.user_following

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
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
class UserFollowingUseCaseTest {

    private lateinit var userFollowingUseCase: UserFollowingUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var userDao: UserDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        userFollowingUseCase = UserFollowingUseCase(gamesApi, userDao, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `getFollowingUsers, gets from UserDao`() {
        //Assemble
        val expectedResult = flowOf(Outcome.empty())
        every { userDao.getFollowingUsers() } returns expectedResult
        //Act
        val result = userFollowingUseCase.getFollowingUsers()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updateFollowingUsers, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            coEvery { gamesApi.getFollowingUsers() } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = userFollowingUseCase.updateFollowingUsers()
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updateFollowingUsers, calls getFollowingUsers from gamesApi`() =
        runBlockingTest {
            //Assemble
            //Act
            userFollowingUseCase.updateFollowingUsers()
            //Assert
            coVerify { gamesApi.getFollowingUsers() }
        }

    @Test
    fun `updateFollowingUsers, sets Loading state, then sets Result state`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getFollowingUsers() } returns NetworkResponse(
                isSuccess = true,
                data = listOf()
            )
            val expectedResult = Outcome.empty()
            //Act
            userFollowingUseCase.updateFollowingUsers()
            //Assert
            verifySequence {
                userDao.setFollowingUsersOutcome(Outcome.loading())
                userDao.setFollowingUsersOutcome(expectedResult)
            }
        }
}