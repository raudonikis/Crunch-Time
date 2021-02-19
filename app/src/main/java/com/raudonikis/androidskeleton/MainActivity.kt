package com.raudonikis.androidskeleton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.raudonikis.androidskeleton.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        lifecycleScope.launchWhenCreated {
            navigationHandler.setUpNavigation(navController)
        }
    }

    /*override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        when (navController.currentDestination?.parent?.id) {
            R.id.navigation_home -> {
                if (navController.currentDestination?.id == R.id.homeFragment) {
                    finish()
                } else {
                    super.onBackPressed()
                }
            }
            R.id.navigation_login -> {
                super.onBackPressed()
            }
            else -> {
                binding.bottomNavigation.selectedItemId = R.id.navigation_home
            }
        }
    }*/
}