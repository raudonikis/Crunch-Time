package com.raudonikis.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.user.UserPreferences
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
        MutableStateFlow(LoginState.INITIAL)
    private val _loginValidationState: StateFlow<LoginValidationState> =
        combine(_emailState, _passwordState, _loginState) { email, password, login ->
            when {
                email.isValid() && password.isValid() && login != LoginState.LOADING -> LoginValidationState.ENABLED
                else -> LoginValidationState.DISABLED
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, LoginValidationState.DISABLED)
    private val _loginEvent: Channel<LoginEvent> = Channel(capacity = Channel.BUFFERED)

    /**
     * Observables
     */
    val emailState: Flow<EmailState> = _emailState
    val passwordState: Flow<PasswordState> = _passwordState
    val loginState: Flow<LoginState> = _loginState
    val loginValidationState: Flow<LoginValidationState> = _loginValidationState
    val loginEvent: Flow<LoginEvent> = _loginEvent.receiveAsFlow()

    /**
     * Login
     */
    private fun login() {
        _loginState.value = LoginState.LOADING
        viewModelScope.launch {
            val email = _emailState.value.getCurrentEmail()
            val password = _passwordState.value.getCurrentPassword()
            authenticationRepository.login(email, password)
                .onSuccess {
                    _loginEvent.offer(LoginEvent.LoginSuccess)
                    navigateToBottomNavigation()
                }
                .onFailure { errorMessage ->
                    _loginState.value = LoginState.INITIAL
                    _loginEvent.offer(LoginEvent.LoginFailure(errorMessage))
                }
                .onEmpty {
                    _loginState.value = LoginState.INITIAL
                    _loginEvent.offer(LoginEvent.LoginResponseEmpty)
                }
        }
    }

    /**
     * Events
     */
    fun onViewCreated() {
        if (userPreferences.isRememberMeChecked) {
            userPreferences.currentUser?.let { user ->
                _loginEvent.offer(LoginEvent.InitialiseFields(user))
            }
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
        clearValidationStates()
    }

    fun onLoginClicked() {
        if (_loginValidationState.value == LoginValidationState.ENABLED) {
            login()
        }
    }

    private fun clearValidationStates() {
        _passwordState.value = PasswordState.Initial
        _emailState.value = EmailState.Initial
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