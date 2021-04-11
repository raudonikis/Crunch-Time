package com.raudonikis.settings

import android.content.SharedPreferences
import com.raudonikis.common.extensions.get
import com.raudonikis.common.extensions.put
import com.raudonikis.common_ui.extensions.observeKey
import com.raudonikis.settings.app_theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    var selectedTheme: AppTheme
        get() {
            val themeString = sharedPreferences.get(KEY_SELECTED_THEME, AppTheme.DEFAULT.toString())
            return AppTheme.fromString(themeString)
        }
        set(value) {
            sharedPreferences.put(KEY_SELECTED_THEME, value.toString())
        }

    fun selectedThemeAsFlow(): Flow<AppTheme> =
        sharedPreferences.observeKey(KEY_SELECTED_THEME, AppTheme.DEFAULT.toString()).map {
            AppTheme.fromString(it)
        }

    companion object {
        private const val KEY_SELECTED_THEME = "selected_theme"
    }
}