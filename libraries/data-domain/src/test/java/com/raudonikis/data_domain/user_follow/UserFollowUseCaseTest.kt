package com.raudonikis.data_domain.user_follow

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.NetworkResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserFollowUseCaseTest {

    private lateinit var userFollowUseCase: UserFollowUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var userDao: UserDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        userFollowUseCase =
            UserFollowUseCase(gamesApi, userDao, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `followUser, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            coEvery { gamesApi.followUser(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = userFollowUseCase.followUser(user)
            //Assert
            Assert.assertEquals(expectedResult, result)
        }

    @Test
    fun `followUser, calls followUser from gamesApi`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            //Act
            userFollowUseCase.followUser(user)
            //Assert
            coVerify { gamesApi.followUser(user.uuid) }
        }

    @Test
    fun `followUser, onEmpty or onSuccess, adds new following user`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            coEvery { gamesApi.followUser(any()) } returns NetworkResponse(
                isSuccess = true,
                data = listOf()
            )
            //Act
            userFollowUseCase.followUser(user)
            //Assert
            verify { userDao.addNewFollowingUser(user) }
        }

    @Test
    fun `followUser, onFailure, does not add a new following user`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            coEvery { gamesApi.followUser(any()) } returns NetworkResponse(isSuccess = false)
            //Act
            userFollowUseCase.followUser(user)
            //Assert
            verify(exactly = 0) { userDao.addNewFollowingUser(user) }
        }

    @Test
    fun `unfollowUser, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            coEvery { gamesApi.unfollowUser(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = userFollowUseCase.unfollowUser(user)
            //Assert
            Assert.assertEquals(expectedResult, result)
        }

    @Test
    fun `unfollowUser, calls unfollowUser from gamesApi`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            //Act
            userFollowUseCase.unfollowUser(user)
            //Assert
            coVerify { gamesApi.unfollowUser(user.uuid) }
        }

    @Test
    fun `unfollowUser, onEmpty or onSuccess, removes following user`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            coEvery { gamesApi.unfollowUser(any()) } returns NetworkResponse(
                isSuccess = true,
                data = listOf()
            )
            //Act
            userFollowUseCase.unfollowUser(user)
            //Assert
            verify { userDao.removeFollowingUser(user) }
        }

    @Test
    fun `unfollowUser, onFailure, does not remove a following user`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", true)
            coEvery { gamesApi.unfollowUser(any()) } returns NetworkResponse(isSuccess = false)
            //Act
            userFollowUseCase.unfollowUser(user)
            //Assert
            verify(exactly = 0) { userDao.removeFollowingUser(user) }
        }
}