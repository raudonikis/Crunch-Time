package com.raudonikis.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.login.LoginRouter
import com.raudonikis.login.validation.EmailValidationResult
import com.raudonikis.login.validation.PasswordValidationResult
import com.raudonikis.login.validation.ValidationUtils
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    /**
     * States
     */
    private val emailState: MutableStateFlow<EmailValidationResult> =
        MutableStateFlow(EmailValidationResult.EMAIL_INITIAL)
    private val passwordState: MutableStateFlow<PasswordValidationResult> =
        MutableStateFlow(PasswordValidationResult.PASSWORD_INITIAL)
    private val loginState: MutableStateFlow<LoginState> =
        MutableStateFlow(LoginState.Initial)

    /**
     * Observables
     */
    fun emailStateObservable() = emailState.asLiveData(viewModelScope.coroutineContext)
    fun passwordStateObservable() = passwordState.asLiveData(viewModelScope.coroutineContext)
    fun loginStateObservable() = loginState.asLiveData(viewModelScope.coroutineContext)

    /**
     * Login
     */
    fun login(email: String, password: String) {
        if (emailState.value.isValid() && passwordState.value.isValid()) {
            // try to login
            loginState.value = LoginState.Loading
            viewModelScope.launch {
                val loginSuccess = authenticationRepository.login(email, password)
                if (loginSuccess) {
                    onLoginSuccess()
                } else {
                    loginState.value = LoginState.LoginFailure
                }
            }
        } else {
            // Invalid inputs
            loginState.value = LoginState.InvalidInputs
        }
    }

    private fun onLoginSuccess() {
        loginState.value = LoginState.LoginSuccess
        navigateToBottomNavigation()
    }

    /**
     * Validation
     */
    fun onEmailChanged(email: String) {
        emailState.value = ValidationUtils.validateEmail(email)
    }

    fun onPasswordChanged(password: String) {
        passwordState.value = ValidationUtils.validatePassword(password)
    }

    /**
     * Navigation
     */
    fun navigateToSignUp() {
        navigationDispatcher.navigate(LoginRouter.loginToSignUp())
    }

    fun navigateToBottomNavigation() {
        navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true))
    }
}