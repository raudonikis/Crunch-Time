package com.raudonikis.androidskeleton

import androidx.lifecycle.ViewModel
import com.raudonikis.data.auth.AuthPreferences
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import com.raudonikis.settings.SettingsPreferences
import com.raudonikis.settings.app_theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsPreferences: SettingsPreferences,
    authPreferences: AuthPreferences,
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    init {
        if (authPreferences.isUserAuthenticated()) {
            navigateToBottomNavigation()
        }
    }

    /**
     * Observables
     */
    val themeState: Flow<AppTheme> = settingsPreferences.selectedThemeAsFlow()

    /**
     * Navigation
     */
    private fun navigateToBottomNavigation() {
        navigationDispatcher.navigate(NavigationGraph.BottomNavigation(inclusive = true))
    }
}