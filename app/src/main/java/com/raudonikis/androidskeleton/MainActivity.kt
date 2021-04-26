package com.raudonikis.androidskeleton

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.raudonikis.androidskeleton.databinding.ActivityMainBinding
import com.raudonikis.bottomnavigation.NavigationHandler
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.settings.app_theme.AppTheme
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var navigationHandler: NavigationHandler
    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setUpNavigation()
        setUpThemeSelection()
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        lifecycleScope.launchWhenCreated {
            navigationHandler.setUpNavigation(navController, onIntent = {
                startActivity(it)
            })
        }
    }

    private fun setUpThemeSelection() {
        viewModel.themeState
            .onEach { onThemeChanged(it) }
            .observeInLifecycle(this)
    }

    /**
     * Events
     */
    private fun onThemeChanged(themeState: AppTheme) {
        when (themeState) {
            AppTheme.DEFAULT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            AppTheme.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            AppTheme.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}