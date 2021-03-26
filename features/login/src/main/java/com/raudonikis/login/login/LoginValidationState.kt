package com.raudonikis.login.login

sealed class LoginValidationState {
    object Disabled: LoginValidationState()
    object Enabled: LoginValidationState()
}
