package com.raudonikis.home

import androidx.lifecycle.ViewModel
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    fun navigateToHomeNext() {
        navigationDispatcher.navigate(HomeRouter.homeToHomeNext())
    }
}