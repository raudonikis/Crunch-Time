package com.raudonikis.login.login

import androidx.lifecycle.ViewModel
import com.raudonikis.login.LoginRouter
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    fun navigateToSignUp() {
        navigationDispatcher.navigate(LoginRouter.loginToSignUp())
    }

    fun navigateToHome() {
        navigationDispatcher.navigate(NavigationGraph.Home(true))
    }
}