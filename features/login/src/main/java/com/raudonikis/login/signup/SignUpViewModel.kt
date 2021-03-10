package com.raudonikis.login.signup

import androidx.lifecycle.ViewModel
import com.raudonikis.login.validation.EmailValidationResult
import com.raudonikis.login.validation.PasswordValidationResult
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

    private val _emailState: MutableStateFlow<EmailValidationResult> =
        MutableStateFlow(EmailValidationResult.EMAIL_INITIAL)
    private val _passwordState: MutableStateFlow<PasswordValidationResult> =
        MutableStateFlow(PasswordValidationResult.PASSWORD_INITIAL)

    /**
     * Observables
     */
    val emailState: StateFlow<EmailValidationResult> = _emailState
    val passwordStateObservable: StateFlow<PasswordValidationResult> = _passwordState

    /**
     * Sign Up
     */
    fun signUp(email: String?, password: String?) {

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

    /**
     * Navigation
     */
    fun navigateToLogin() {
        navigationDispatcher.navigateBack()
    }
}