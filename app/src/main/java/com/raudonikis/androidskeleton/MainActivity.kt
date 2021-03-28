package com.raudonikis.androidskeleton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.raudonikis.androidskeleton.databinding.ActivityMainBinding
import com.raudonikis.bottomnavigation.NavigationHandler
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var navigationHandler: NavigationHandler
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        lifecycleScope.launchWhenCreated {
            navigationHandler.setUpNavigation(navController, onIntent = {
                startActivity(it)
            })
        }
    }
}