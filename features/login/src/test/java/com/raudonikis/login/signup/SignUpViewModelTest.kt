package com.raudonikis.login.signup

import app.cash.turbine.test
import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.login.validation.ValidationUtils
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import com.raudonikis.network.auth.UserResponse
import com.raudonikis.network.auth.register.RegisterResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class SignUpViewModelTest {

    private lateinit var sut: SignUpViewModel

    @RelaxedMockK
    private lateinit var navigationDispatcher: NavigationDispatcher

    @RelaxedMockK
    private lateinit var authenticationRepository: AuthenticationRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut =
            SignUpViewModel(
                navigationDispatcher,
                authenticationRepository,
            )
    }

    @Test
    fun `onLoginClicked, navigates back to login`() {
        //Assemble
        //Act
        sut.onLoginClicked()
        //Assert
        verify { navigationDispatcher.navigateBack() }
    }

    @Test
    fun `onEmailChanged, updates EmailState`() {
        //Assemble
        val email = "Email"
        val expectedResult = ValidationUtils.validateEmail(email)
        //Act
        sut.onEmailChanged(email)
        //Assert
        Assert.assertEquals(expectedResult, sut.emailState.value)
    }

    @Test
    fun `onPasswordChanged, updates PasswordState`() {
        //Assemble
        val pass = "pass"
        val expectedResult = ValidationUtils.validatePassword(pass)
        //Act
        sut.onPasswordChanged(pass)
        //Assert
        Assert.assertEquals(expectedResult, sut.passwordState.value)
    }

    @Test
    fun `onPasswordConfirmChanged, updates PasswordConfirmState`() {
        //Assemble
        val pass = "pass"
        val expectedResult = ValidationUtils.validateConfirmPassword(pass, pass)
        //Act
        sut.onPasswordConfirmChanged(pass)
        //Assert
        Assert.assertEquals(expectedResult, sut.passwordConfirmState.value)
    }

    @Test
    fun `onUsernameChanged, updates UsernameState`() {
        //Assemble
        val username = "username"
        val expectedResult = ValidationUtils.validateUsername(username)
        //Act
        sut.onUsernameChanged(username)
        //Assert
        Assert.assertEquals(expectedResult, sut.usernameState.value)
    }

    @Test
    fun `onSignUpClicked, if signup validation state is DISABLED, does not call register`() {
        //Assemble
        //Act
        sut.onSignUpClicked()
        //Assert
        coVerify(exactly = 0) { authenticationRepository.register(any(), any(), any(), any()) }
    }

    @Test
    fun `onSignUpClicked, if signup validation state is ENABLED, calls register`() =
        runBlockingTest {
            //Assemble
            val email = "email@email.com"
            val password = "password"
            val username = "username"
            //Act
            sut.signUpValidationState.test {
                sut.onEmailChanged(email)
                sut.onPasswordChanged(password)
                sut.onPasswordConfirmChanged(password)
                sut.onUsernameChanged(username)
                sut.onSignUpClicked()
                cancelAndIgnoreRemainingEvents()
            }
            //Assert
            coVerify { authenticationRepository.register(email, password, password, username) }
        }

    @Test
    fun `onSignUpClicked, if success, navigates to bottomNavigation`() = runBlockingTest {
        //Assemble
        val email = "email@email.com"
        val password = "password"
        val username = "username"
        coEvery {
            authenticationRepository.register(
                any(),
                any(),
                any(),
                any()
            )
        } returns Outcome.success(
            RegisterResponse(
                UserResponse(
                    "", "", "", false
                ), ""
            )
        )
        //Act
        sut.signUpValidationState.test {
            sut.onEmailChanged(email)
            sut.onPasswordChanged(password)
            sut.onPasswordConfirmChanged(password)
            sut.onUsernameChanged(username)
            sut.onSignUpClicked()
            cancelAndIgnoreRemainingEvents()
        }
        //Assert
        verify { navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true)) }
    }

    @Test
    fun `onSignUpClicked, if register empty, does not navigate to bottomNavigation`() =
        runBlockingTest {
            //Assemble
            val email = "email@email.com"
            val password = "password"
            val username = "username"
            coEvery {
                authenticationRepository.register(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Outcome.empty()
            //Act
            sut.signUpValidationState.test {
                sut.onEmailChanged(email)
                sut.onPasswordChanged(password)
                sut.onPasswordConfirmChanged(password)
                sut.onUsernameChanged(username)
                sut.onSignUpClicked()
                cancelAndIgnoreRemainingEvents()
            }
            //Assert
            verify(exactly = 0) {
                navigationDispatcher.navigate(
                    NavigationGraph.BottomNavigation(true)
                )
                navigationDispatcher.navigate(
                    NavigationGraph.BottomNavigation(false)
                )
            }
        }

    @Test
    fun `onSignUpClicked, if register failure, does not navigate to bottomNavigation`() =
        runBlockingTest {
            //Assemble
            val email = "email@email.com"
            val password = "password"
            val username = "username"
            coEvery {
                authenticationRepository.register(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Outcome.failure()
            //Act
            sut.signUpValidationState.test {
                sut.onEmailChanged(email)
                sut.onPasswordChanged(password)
                sut.onPasswordConfirmChanged(password)
                sut.onUsernameChanged(username)
                sut.onSignUpClicked()
                cancelAndIgnoreRemainingEvents()
            }
            //Assert
            verify(exactly = 0) {
                navigationDispatcher.navigate(
                    NavigationGraph.BottomNavigation(true)
                )
                navigationDispatcher.navigate(
                    NavigationGraph.BottomNavigation(false)
                )
            }
        }
}