package com.raudonikis.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raudonikis.login.validation.EmailValidationResult
import com.raudonikis.login.validation.PasswordValidationResult
import com.raudonikis.login.validation.ValidationUtils
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    private val emailState: MutableStateFlow<EmailValidationResult> =
        MutableStateFlow(EmailValidationResult.EMAIL_INITIAL)
    private val passwordState: MutableStateFlow<PasswordValidationResult> =
        MutableStateFlow(PasswordValidationResult.PASSWORD_INITIAL)

    /**
     * Observables
     */
    fun emailStateObservable() = emailState.asLiveData(viewModelScope.coroutineContext)
    fun passwordStateObservable() = passwordState.asLiveData(viewModelScope.coroutineContext)

    /**
     * Sign Up
     */
    fun signUp(email: String?, password: String?) {

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
    fun navigateToLogin() {
        navigationDispatcher.navigateBack()
    }
}