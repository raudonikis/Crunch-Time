package com.raudonikis.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.auth.AuthenticationRepository
import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.UsernameState
import com.raudonikis.login.validation.ValidationUtils
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    /**
     * States/Events
     */
    private val _emailState: MutableStateFlow<EmailState> =
        MutableStateFlow(EmailState.Initial)
    private val _passwordState: MutableStateFlow<PasswordState> =
        MutableStateFlow(PasswordState.Initial)
    private val _passwordConfirmState: MutableStateFlow<PasswordState> =
        MutableStateFlow(PasswordState.Initial)
    private val _usernameState: MutableStateFlow<UsernameState> =
        MutableStateFlow(UsernameState.Initial)
    private val _signUpState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState.INITIAL)
    private val _signUpValidationValidationState: StateFlow<SignUpValidationState> =
        combine(
            _emailState,
            _passwordState,
            _passwordConfirmState,
            _usernameState,
            _signUpState
        ) { email, password, passwordConfirm, username, signUp ->
            when {
                email.isValid() && password.isValid() && passwordConfirm.isValid() && username.isValid() && signUp != SignUpState.LOADING -> {
                    SignUpValidationState.ENABLED
                }
                else -> SignUpValidationState.DISABLED
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, SignUpValidationState.DISABLED)
    private val _signUpEvent: Channel<SignUpEvent> = Channel()

    /**
     * Observables
     */
    val emailState: Flow<EmailState> = _emailState
    val passwordState: Flow<PasswordState> = _passwordState
    val passwordConfirmState: Flow<PasswordState> = _passwordConfirmState
    val usernameState: Flow<UsernameState> = _usernameState
    val signUpState: Flow<SignUpState> = _signUpState
    val signUpEvent: Flow<SignUpEvent> = _signUpEvent.receiveAsFlow()
    val signUpValidationState: Flow<SignUpValidationState> = _signUpValidationValidationState


    private fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            _signUpState.value = SignUpState.LOADING
            val email = _emailState.value.getCurrentEmail()
            val password = _passwordState.value.getCurrentPassword()
            val passwordConfirm = _passwordConfirmState.value.getCurrentPassword()
            val username = _usernameState.value.getCurrentUsername()
            authenticationRepository.register(email, password, passwordConfirm, username)
                .onSuccess {
                    _signUpState.value = SignUpState.INITIAL
                    _signUpEvent.offer(SignUpEvent.SUCCESS)
                    navigateToBottomNavigation()
                }
                .onFailure {
                    _signUpState.value = SignUpState.INITIAL
                    _signUpEvent.offer(SignUpEvent.FAILURE)
                }
                .onEmpty {
                    _signUpState.value = SignUpState.INITIAL
                    _signUpEvent.offer(SignUpEvent.FAILURE)
                }
        }
    }

    /**
     * Events
     */
    fun onEmailChanged(email: String?) {
        _emailState.value = ValidationUtils.validateEmail(email)
    }

    fun onPasswordChanged(password: String?) {
        _passwordState.value = ValidationUtils.validatePassword(password)
    }

    fun onPasswordConfirmChanged(passwordConfirm: String?) {
        val password = _passwordState.value.getCurrentPassword()
        _passwordConfirmState.value =
            ValidationUtils.validateConfirmPassword(password, passwordConfirm)
    }

    fun onUsernameChanged(username: String?) {
        _usernameState.value = ValidationUtils.validateUsername(username)
    }

    fun onLoginClicked() {
        navigateToLogin()
    }

    fun onSignUpClicked() {
        if (_signUpValidationValidationState.value == SignUpValidationState.ENABLED) {
            signUp()
        }
    }

    /**
     * Navigation
     */
    private fun navigateToLogin() {
        navigationDispatcher.navigateBack()
    }

    private fun navigateToBottomNavigation() {
        navigationDispatcher.navigate(NavigationGraph.BottomNavigation(true))
    }
}