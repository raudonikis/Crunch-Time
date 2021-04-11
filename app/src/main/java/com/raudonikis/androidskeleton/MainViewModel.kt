package com.raudonikis.androidskeleton

import androidx.lifecycle.ViewModel
import com.raudonikis.settings.SettingsPreferences
import com.raudonikis.settings.app_theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsPreferences: SettingsPreferences,
) : ViewModel() {

    /**
     * Observables
     */
    val themeState: Flow<AppTheme> = settingsPreferences.selectedThemeAsFlow()
}