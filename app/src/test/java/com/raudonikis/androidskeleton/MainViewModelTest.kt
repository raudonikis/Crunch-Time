package com.raudonikis.androidskeleton

import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.data.auth.AuthPreferences
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import com.raudonikis.settings.SettingsPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var sut: MainViewModel

    @RelaxedMockK
    private lateinit var navigationDispatcher: NavigationDispatcher

    @RelaxedMockK
    private lateinit var settingsPreferences: SettingsPreferences

    @RelaxedMockK
    private lateinit var authPreferences: AuthPreferences

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = MainViewModel(
            settingsPreferences,
            authPreferences,
            navigationDispatcher
        )
    }

    @Test
    fun `init, if user authenticated, navigate to bottom navigation`() {
        //Assemble
        every { authPreferences.isUserAuthenticated() } returns true
        val sut = MainViewModel(
            settingsPreferences,
            authPreferences,
            navigationDispatcher,
        )
        //init
        //Assert
        verify { navigationDispatcher.navigate(NavigationGraph.BottomNavigation(inclusive = true)) }
    }

    @Test
    fun `init, if user not authenticated, do not navigate to bottom navigation`() {
        //Assemble
        every { authPreferences.isUserAuthenticated() } returns false
        val sut = MainViewModel(
            settingsPreferences,
            authPreferences,
            navigationDispatcher,
        )
        //init
        //Assert
        verify(exactly = 0) {
            navigationDispatcher.navigate(NavigationGraph.BottomNavigation(inclusive = true))
            navigationDispatcher.navigate(NavigationGraph.BottomNavigation(inclusive = false))
        }
    }
}