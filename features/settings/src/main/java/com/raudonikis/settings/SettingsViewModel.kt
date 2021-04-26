package com.raudonikis.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import com.raudonikis.settings.app_theme.AppTheme
import com.raudonikis.settings.logout.LogoutEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val authenticationRepository: AuthenticationRepository,
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {

    /**
     * States/Events/Data
     */
    private val _logoutEvent: Channel<LogoutEvent> = Channel()

    /**
     * Observables
     */
    val logoutEvent: Flow<LogoutEvent> = _logoutEvent.receiveAsFlow()

    fun getSelectedTheme(): AppTheme {
        return settingsPreferences.selectedTheme
    }

    /**
     * Events
     */
    fun onLogoutClicked() {
        _logoutEvent.offer(LogoutEvent.IN_PROGRESS)
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.logout()
                .onSuccess {
                    _logoutEvent.offer(LogoutEvent.SUCCESS)
                    navigateToLogin()
                }
                .onFailure { _logoutEvent.offer(LogoutEvent.FAILURE) }
        }
    }

    fun onThemeChanged(theme: AppTheme) {
        settingsPreferences.selectedTheme = theme
    }

    /**
     * Navigation
     */

    private fun navigateToLogin() {
        navigationDispatcher.navigate(NavigationGraph.Login)
    }
}