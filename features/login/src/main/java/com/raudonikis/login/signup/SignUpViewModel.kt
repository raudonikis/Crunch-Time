package com.raudonikis.login.signup

import androidx.lifecycle.ViewModel
import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.UsernameState
import com.raudonikis.login.validation.ValidationUtils
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    private val _emailState: MutableStateFlow<EmailState> =
        MutableStateFlow(EmailState.Initial)
    private val _passwordState: MutableStateFlow<PasswordState> =
        MutableStateFlow(PasswordState.Initial)
    private val _passwordConfirmState: MutableStateFlow<PasswordState> =
        MutableStateFlow(PasswordState.Initial)
    private val _usernameState: MutableStateFlow<UsernameState> =
        MutableStateFlow(UsernameState.Initial)

    /**
     * Observables
     */
    val emailState: StateFlow<EmailState> = _emailState
    val passwordState: StateFlow<PasswordState> = _passwordState
    val passwordConfirmState: StateFlow<PasswordState> = _passwordConfirmState
    val usernameState: StateFlow<UsernameState> = _usernameState

    /**
     * Sign Up
     */
    fun signUp(email: String?, password: String?, passwordConfirm: String?, username: String?) {

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

    /**
     * Navigation
     */
    fun navigateToLogin() {
        navigationDispatcher.navigateBack()
    }
}