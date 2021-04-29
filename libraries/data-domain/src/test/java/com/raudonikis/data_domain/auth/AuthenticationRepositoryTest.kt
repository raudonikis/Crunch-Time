package com.raudonikis.data_domain.auth

import com.raudonikis.common.Outcome
import com.raudonikis.data.auth.AuthPreferences
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.data_domain.user.mappers.UserMapper
import com.raudonikis.data_domain.user_search.UserSearchUseCase
import com.raudonikis.network.GamesApi
import com.raudonikis.network.auth.UserResponse
import com.raudonikis.network.auth.login.LoginResponse
import com.raudonikis.network.auth.register.RegisterResponse
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
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthenticationRepositoryTest {

    private lateinit var authenticationRepository: AuthenticationRepository

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var userPreferences: UserPreferences

    @RelaxedMockK
    private lateinit var authPreferences: AuthPreferences

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        authenticationRepository =
            AuthenticationRepository(gamesApi, userPreferences, authPreferences, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `logout, called logout from gamesApi`() = runBlockingTest {
        //Assemble
        //Act
        authenticationRepository.logout()
        //Assert
        coVerify { gamesApi.logout() }
    }

    @Test
    fun `logout, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.logout() } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = authenticationRepository.logout()
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `logout, if success, clear user data`() = runBlockingTest {
        //Assemble
        val expectedResult = NetworkResponse<List<Nothing>>(isSuccess = true, data = listOf())
        coEvery { gamesApi.logout() } returns expectedResult
        //Act
        authenticationRepository.logout()
        //Assert
        coVerify { authPreferences.clearUserData() }
    }

    @Test
    fun `logout, if failure, do not clear user data`() = runBlockingTest {
        //Assemble
        val expectedResult = NetworkResponse<List<Nothing>>(isSuccess = false)
        coEvery { gamesApi.logout() } returns expectedResult
        //Act
        authenticationRepository.logout()
        //Assert
        coVerify(exactly = 0) { authPreferences.clearUserData() }
    }

    @Test
    fun `login, called login from gamesApi`() = runBlockingTest {
        //Assemble
        //Act
        authenticationRepository.login("", "")
        //Assert
        coVerify { gamesApi.login(any()) }
    }

    @Test
    fun `login, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.login(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = authenticationRepository.login("", "")
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `login, onSuccess, set access token`() =
        runBlockingTest {
            //Assemble
            val accessToken = "test-token"
            val loginResponse = LoginResponse(
                UserResponse("", "", "", false), accessToken
            )
            coEvery { gamesApi.login(any()) } returns NetworkResponse(
                isSuccess = true,
                data = loginResponse
            )
            //Act
            authenticationRepository.login("", "")
            //Assert
            verify { authPreferences.accessToken = accessToken }
        }

    @Test
    fun `login, not success, do not set access token`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.login(any()) } returns NetworkResponse(isSuccess = false)
            //Act
            authenticationRepository.login("", "")
            //Assert
            verify(exactly = 0) { authPreferences.accessToken = any() }
        }

    @Test
    fun `login, onSuccess, set current user`() =
        runBlockingTest {
            //Assemble
            val accessToken = "test-token"
            val loginResponse = LoginResponse(
                UserResponse("", "", "", false), accessToken
            )
            val user = UserMapper.fromUserResponse(loginResponse.userResponse)
            coEvery { gamesApi.login(any()) } returns NetworkResponse(
                isSuccess = true,
                data = loginResponse
            )
            //Act
            authenticationRepository.login("", "")
            //Assert
            verify { userPreferences.currentUser = user }
        }

    @Test
    fun `login, not success, do not set current user`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.login(any()) } returns NetworkResponse(isSuccess = false)
            //Act
            authenticationRepository.login("", "")
            //Assert
            verify(exactly = 0) { userPreferences.currentUser = any() }
        }

    @Test
    fun `register, called register from gamesApi`() = runBlockingTest {
        //Assemble
        //Act
        authenticationRepository.register("", "", "", "")
        //Assert
        coVerify { gamesApi.register(any()) }
    }

    @Test
    fun `register, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.register(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = authenticationRepository.register("", "", "", "")
            //Assert
            assertEquals(expectedResult, result)
        }

    @Test
    fun `register, onSuccess, set access token`() =
        runBlockingTest {
            //Assemble
            val accessToken = "test-token"
            val registerResponse = RegisterResponse(
                UserResponse("", "", "", false), accessToken
            )
            coEvery { gamesApi.register(any()) } returns NetworkResponse(
                isSuccess = true,
                data = registerResponse
            )
            //Act
            authenticationRepository.register("", "", "", "")
            //Assert
            verify { authPreferences.accessToken = accessToken }
        }

    @Test
    fun `register, not success, do not set access token`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.register(any()) } returns NetworkResponse(isSuccess = false)
            //Act
            authenticationRepository.register("", "", "", "")
            //Assert
            verify(exactly = 0) { authPreferences.accessToken = any() }
        }

    @Test
    fun `register, onSuccess, set current user`() =
        runBlockingTest {
            //Assemble
            val accessToken = "test-token"
            val registerResponse = RegisterResponse(
                UserResponse("", "", "", false), accessToken
            )
            val user = UserMapper.fromUserResponse(registerResponse.user)
            coEvery { gamesApi.register(any()) } returns NetworkResponse(
                isSuccess = true,
                data = registerResponse
            )
            //Act
            authenticationRepository.register("", "", "", "")
            //Assert
            verify { userPreferences.currentUser = user }
        }

    @Test
    fun `register, not success, do not set current user`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.register(any()) } returns NetworkResponse(isSuccess = false)
            //Act
            authenticationRepository.register("", "", "", "")
            //Assert
            verify(exactly = 0) { userPreferences.currentUser = any() }
        }
}