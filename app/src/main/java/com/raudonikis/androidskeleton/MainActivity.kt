package com.raudonikis.androidskeleton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.raudonikis.androidskeleton.databinding.ActivityMainBinding
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.show
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
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.signUpFragment -> {
                    binding.bottomNavigation.hide()
                }
                else -> {
                    binding.bottomNavigation.show()
                }
            }
        }
    }

    override fun onBackPressed() {
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
    }
}