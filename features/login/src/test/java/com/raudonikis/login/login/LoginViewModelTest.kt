package com.raudonikis.login.login

import app.cash.turbine.test
import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.core.providers.CoroutineDispatcherProvider
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.login.LoginRouter
import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.ValidationUtils
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import com.raudonikis.network.auth.UserResponse
import com.raudonikis.network.auth.login.LoginResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var sut: LoginViewModel

    @RelaxedMockK
    private lateinit var navigationDispatcher: NavigationDispatcher

    @RelaxedMockK
    private lateinit var authenticationRepository: AuthenticationRepository

    @RelaxedMockK
    private lateinit var userPreferences: UserPreferences

    private val testDispatcher = TestCoroutineDispatcher()
    private val testDispatcherProvider = CoroutineDispatcherProvider(
        testDispatcher,
        testDispatcher,
        testDispatcher
    )

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = spyk(
            LoginViewModel(
                navigationDispatcher,
                authenticationRepository,
                userPreferences,
                testDispatcherProvider
            )
        )
        coEvery { authenticationRepository.login(any(), any()) } returns Outcome.empty()
    }

    @Test
    fun `onRememberMeChecked, sets the remember me value in userPreferences`() {
        //Assemble
        val isChecked = true
        //Act
        sut.onRememberMeChecked(isChecked)
        //Assert
        verify { userPreferences.isRememberMeChecked = isChecked }
    }

    @Test
    fun `onEmailChanged, updates EmailState`() {
        //Assemble
        val email = "Email"
        val expectedResult = ValidationUtils.validateEmail(email)
        //Act
        sut.onEmailChanged(email)
        //Assert
        assertEquals(expectedResult, sut.emailState.value)
    }

    @Test
    fun `onPasswordChanged, updates PasswordState`() {
        //Assemble
        val pass = "pass"
        val expectedResult = ValidationUtils.validatePassword(pass)
        //Act
        sut.onPasswordChanged(pass)
        //Assert
        assertEquals(expectedResult, sut.passwordState.value)
    }

    @Test
    fun `onSignUpClicked, clears email state`() {
        //Assemble
        //Act
        sut.onSignUpClicked()
        //Assert
        assertEquals(EmailState.Initial, sut.emailState.value)
    }

    @Test
    fun `onSignUpClicked, clears password state`() {
        //Assemble
        //Act
        sut.onSignUpClicked()
        //Assert
        assertEquals(PasswordState.Initial, sut.passwordState.value)
    }

    @Test
    fun `onSignUpClicked, navigates to SignUp`() {
        //Assemble
        //Act
        sut.onSignUpClicked()
        //Assert
        verify { navigationDispatcher.navigate(LoginRouter.loginToSignUp()) }
    }

    @Test
    fun `onLoginClicked, if login validation state is DISABLED, does not call login`() {
        //Assemble
        //Act
        sut.onLoginClicked()
        //Assert
        coVerify(exactly = 0) { authenticationRepository.login(any(), any()) }
    }

    @Test
    fun `onLoginClicked, if login validation state is ENABLED, calls login`() = runBlockingTest {
        //Assemble
        //Act
        sut.loginValidationState.test {
            sut.onEmailChanged("email@email.com")
            sut.onPasswordChanged("password")
            sut.onLoginClicked()
            cancelAndIgnoreRemainingEvents()
        }
        //Assert
        coVerify { authenticationRepository.login(any(), any()) }
    }

    @Test
    fun `onLoginClicked, if login success, navigates to bottomNavigation`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.login(any(), any()) } returns Outcome.success(
            LoginResponse(
                UserResponse(
                    "", "", "", false
                ), ""
            )
        )
        //Act
        sut.loginValidationState.test {
            sut.onEmailChanged("email@email.com")
            sut.onPasswordChanged("password")
            sut.onLoginClicked()
            cancelAndIgnoreRemainingEvents()
        }
        //Assert
        verify { navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true)) }
    }

    @Test
    fun `onLoginClicked, if login empty, does not navigate to bottomNavigation`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.login(any(), any()) } returns Outcome.empty()
        //Act
        sut.loginValidationState.test {
            sut.onEmailChanged("email@email.com")
            sut.onPasswordChanged("password")
            sut.onLoginClicked()
            cancelAndIgnoreRemainingEvents()
        }
        //Assert
        verify(exactly = 0) { navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true)) }
    }

    @Test
    fun `onLoginClicked, if login failure, does not navigate to bottomNavigation`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.login(any(), any()) } returns Outcome.failure()
        //Act
        sut.loginValidationState.test {
            sut.onEmailChanged("email@email.com")
            sut.onPasswordChanged("password")
            sut.onLoginClicked()
            cancelAndIgnoreRemainingEvents()
        }
        //Assert
        verify(exactly = 0) { navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true)) }
    }

    @Test
    fun `onViewCreated, if rememberMeChecked and user exists, emits initialise event`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", false)
            every { userPreferences.isRememberMeChecked } returns true
            every { userPreferences.currentUser } returns user
            sut.loginEvent.test {
                //Act
                sut.onViewCreated()
                //Assert
                assertEquals(LoginEvent.InitialiseFields(user), expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onViewCreated, if rememberMeChecked false, does not emit initialise event`() =
        runBlockingTest {
            //Assemble
            val user = User("", "", "", false)
            every { userPreferences.isRememberMeChecked } returns false
            every { userPreferences.currentUser } returns user
            sut.loginEvent.test {
                //Act
                sut.onViewCreated()
                //Assert
                expectNoEvents()
            }
        }

    @Test
    fun `onViewCreated, if current user does not exist, does not emit initialise event`() =
        runBlockingTest {
            //Assemble
            every { userPreferences.isRememberMeChecked } returns true
            every { userPreferences.currentUser } returns null
            sut.loginEvent.test {
                //Act
                sut.onViewCreated()
                //Assert
                expectNoEvents()
            }
        }

    @Test
    fun `loginValidationState, emits correct validation states, when input changes`() =
        runBlockingTest {
            //Assemble
            sut.loginValidationState.test {
                //Act
                val initial = expectItem()
                sut.onEmailChanged("email@email.com")
                sut.onPasswordChanged("password")
                val enabled = expectItem()
                sut.onPasswordChanged("bad")
                val disabled = expectItem()
                //Assert
                assertEquals(initial, LoginValidationState.DISABLED)
                assertEquals(enabled, LoginValidationState.ENABLED)
                assertEquals(disabled, LoginValidationState.DISABLED)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loginValidationState, emits correct validation states, when login in progress`() =
        runBlockingTest {
            //Assemble
            sut.loginValidationState.test {
                //Act
                val initial = expectItem()
                sut.onEmailChanged("email@email.com")
                sut.onPasswordChanged("password")
                val enabled = expectItem()
                sut.onLoginClicked()
                val loading = expectItem()
                val loginFinished = expectItem()
                //Assert
                assertEquals(initial, LoginValidationState.DISABLED)
                assertEquals(enabled, LoginValidationState.ENABLED)
                assertEquals(loading, LoginValidationState.DISABLED)
                assertEquals(loginFinished, LoginValidationState.ENABLED)
            }
        }
}