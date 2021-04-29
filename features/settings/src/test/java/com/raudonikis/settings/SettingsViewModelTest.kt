package com.raudonikis.settings

import app.cash.turbine.test
import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import com.raudonikis.settings.app_theme.AppTheme
import com.raudonikis.settings.logout.LogoutEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    private lateinit var sut: SettingsViewModel

    @RelaxedMockK
    private lateinit var navigationDispatcher: NavigationDispatcher

    @RelaxedMockK
    private lateinit var authenticationRepository: AuthenticationRepository

    @RelaxedMockK
    private lateinit var settingsPreferences: SettingsPreferences

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = SettingsViewModel(
            navigationDispatcher,
            authenticationRepository,
            settingsPreferences,
        )
    }

    @Test
    fun `onLogoutClicked, calls logout from authenticationRepository`() {
        //Assemble
        //Act
        sut.onLogoutClicked()
        //Assert
        coVerify { authenticationRepository.logout() }
    }

    @Test
    fun `onLogoutClicked, first logout event is IN_PROGRESS`() = runBlockingTest {
        //Assemble
        sut.logoutEvent.test {
            //Act
            sut.onLogoutClicked()
            //Assert
            assertEquals(LogoutEvent.IN_PROGRESS, expectItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogoutClicked, if Outcome_Empty, posts success event`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.logout() } returns Outcome.empty()
        sut.logoutEvent.test {
            //Act
            sut.onLogoutClicked()
            //Assert
            expectItem()
            assertEquals(LogoutEvent.SUCCESS, expectItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogoutClicked, if Outcome_Empty, navigates to login`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.logout() } returns Outcome.empty()
        //Act
        sut.onLogoutClicked()
        //Assert
        verify { navigationDispatcher.navigate(NavigationGraph.Login) }
    }

    @Test
    fun `onLogoutClicked, if Outcome_Success, posts success event`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.logout() } returns Outcome.success(listOf())
        sut.logoutEvent.test {
            //Act
            sut.onLogoutClicked()
            //Assert
            expectItem()
            assertEquals(LogoutEvent.SUCCESS, expectItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogoutClicked, if Outcome_Success, navigates to login`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.logout() } returns Outcome.success(listOf())
        //Act
        sut.onLogoutClicked()
        //Assert
        verify { navigationDispatcher.navigate(NavigationGraph.Login) }
    }

    @Test
    fun `onLogoutClicked, if Outcome_Failure, posts failure event`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.logout() } returns Outcome.failure()
        sut.logoutEvent.test {
            //Act
            sut.onLogoutClicked()
            //Assert
            expectItem()
            assertEquals(LogoutEvent.FAILURE, expectItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogoutClicked, if Outcome_Failure, does not navigate to login`() = runBlockingTest {
        //Assemble
        coEvery { authenticationRepository.logout() } returns Outcome.failure()
        //Act
        sut.onLogoutClicked()
        //Assert
        verify(exactly = 0) { navigationDispatcher.navigate(NavigationGraph.Login) }
    }

    @Test
    fun `onThemeChanged, sets the theme in preferences`() {
        //Assemble
        val appTheme = AppTheme.LIGHT
        //Act
        sut.onThemeChanged(appTheme)
        //Assert
        verify { settingsPreferences.selectedTheme = appTheme }
    }
}