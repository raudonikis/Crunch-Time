package com.raudonikis.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data.UserPreferences
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.login.LoginRouter
import com.raudonikis.login.validation.EmailValidationResult
import com.raudonikis.login.validation.PasswordValidationResult
import com.raudonikis.login.validation.ValidationUtils
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
    private val emailState: MutableStateFlow<EmailValidationResult> =
        MutableStateFlow(EmailValidationResult.EMAIL_INITIAL)
    private val passwordState: MutableStateFlow<PasswordValidationResult> =
        MutableStateFlow(PasswordValidationResult.PASSWORD_INITIAL)
    private val loginEvent: MutableSharedFlow<LoginEvent> =
        MutableSharedFlow()

    /**
     * Observables
     */
    val emailStateObservable: Flow<EmailValidationResult> = emailState
    val passwordStateObservable: Flow<PasswordValidationResult> = passwordState
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
        viewModelScope.launch {
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
        }
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
        emailState.value = EmailValidationResult.EMAIL_INITIAL
        passwordState.value = PasswordValidationResult.PASSWORD_INITIAL
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