package com.raudonikis.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data.user.UserPreferences
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.login.LoginRouter
import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.ValidationUtils
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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
    private val _emailState: MutableStateFlow<EmailState> =
        MutableStateFlow(EmailState.Initial)
    private val _passwordState: MutableStateFlow<PasswordState> =
        MutableStateFlow(PasswordState.Initial)
    private val _loginState: MutableStateFlow<LoginState> =
        MutableStateFlow(LoginState.Initial)
    private val _loginValidationValidationState: StateFlow<LoginValidationState> =
        combine(_emailState, _passwordState, _loginState) { email, password, login ->
            when {
                email.isValid() && password.isValid() && login !is LoginState.Loading -> LoginValidationState.Enabled
                else -> LoginValidationState.Disabled
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, LoginValidationState.Disabled)
    private val _loginEvent: Channel<LoginEvent> = Channel(capacity = Channel.BUFFERED)

    /**
     * Observables
     */
    val emailState: Flow<EmailState> = _emailState
    val passwordState: Flow<PasswordState> = _passwordState
    val loginState: Flow<LoginState> = _loginState
    val loginValidationValidationState: Flow<LoginValidationState> = _loginValidationValidationState
    val loginEvent: Flow<LoginEvent> = _loginEvent.receiveAsFlow()

    /**
     * Login
     */
    private fun login() {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val email = _emailState.value.getCurrentEmail()
            val password = _passwordState.value.getCurrentPassword()
            authenticationRepository.login(email, password)
                .onSuccess {
                    _loginEvent.offer(LoginEvent.LoginSuccess)
                    navigateToBottomNavigation()
                }
                .onFailure {
                    _loginState.value = LoginState.Initial
                    _loginEvent.offer(LoginEvent.LoginFailure)
                }
                .onEmpty {
                    _loginState.value = LoginState.Initial
                    _loginEvent.offer(LoginEvent.LoginFailure)
                }
        }
    }

    /**
     * Events
     */
    fun onViewCreated() {
        if (userPreferences.isRememberMeChecked && userPreferences.userEmail.isNotBlank()) {
            _loginEvent.offer(LoginEvent.InitialiseFields(userPreferences.userEmail))
        }
    }

    fun onRememberMeChecked(isChecked: Boolean) {
        userPreferences.isRememberMeChecked = isChecked
    }

    /**
     * Validation
     */
    fun onEmailChanged(email: String) {
        _emailState.value = ValidationUtils.validateEmail(email)
    }

    fun onPasswordChanged(password: String) {
        _passwordState.value = ValidationUtils.validatePassword(password)
    }

    fun onSignUpClicked() {
        navigateToSignUp()
    }

    fun onLoginClicked() {
        if (_loginValidationValidationState.value is LoginValidationState.Enabled) {
            login()
        }
    }

    /**
     * Navigation
     */
    private fun navigateToSignUp() {
        navigationDispatcher.navigate(LoginRouter.loginToSignUp())
    }

    private fun navigateToBottomNavigation() {
        navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true))
    }
}