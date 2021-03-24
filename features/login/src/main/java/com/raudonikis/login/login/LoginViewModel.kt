package com.raudonikis.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data.user.UserPreferences
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.login.LoginRouter
import com.raudonikis.login.validation.*
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val authenticationRepository: AuthenticationRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    /**
     * States/Events
     */
    private val emailState: MutableStateFlow<EmailState> =
        MutableStateFlow(EmailState.Blank)
    private val passwordState: MutableStateFlow<PasswordState> =
        MutableStateFlow(PasswordState.Blank)
    private val loginEvent: MutableSharedFlow<LoginEvent> =
        MutableSharedFlow()

    /**
     * Observables
     */
    val emailStateObservable: Flow<EmailState> = emailState
    val passwordStateObservable: Flow<PasswordState> = passwordState
    val loginEventObservable: Flow<LoginEvent> = loginEvent

    /**
     * Initialisation
     */
    fun onViewCreated() {
        if (userPreferences.isRememberMeChecked && userPreferences.userEmail.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                loginEvent.emit(LoginEvent.InitialiseData(userPreferences.userEmail))
            }
        }
    }

    /**
     * Login
     */
    fun login(email: String, password: String) {
        /*viewModelScope.launch {
            if (emailState.value.isValid() && passwordState.value.isValid()) {
                loginEvent.emit(LoginEvent.Loading)
                authenticationRepository.login(email, password)
                    .onSuccess {
                        onLoginSuccess()
                    }
                    .onFailure {
                        loginEvent.emit(LoginEvent.LoginFailure)
                    }
            } else {
                loginEvent.emit(LoginEvent.InvalidInputs)
            }
        }*/
    }

    private fun onLoginSuccess() {
        viewModelScope.launch {
            loginEvent.emit(LoginEvent.LoginSuccess)
        }
        navigateToBottomNavigation()
    }

    fun onRememberMeChecked(isChecked: Boolean) {
        userPreferences.isRememberMeChecked = isChecked
    }

    private fun resetStates() {
        emailState.value = EmailState.Blank
        passwordState.value = PasswordState.Blank
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
        resetStates()
        navigationDispatcher.navigate(LoginRouter.loginToSignUp())
    }

    private fun navigateToBottomNavigation() {
        navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true))
    }
}