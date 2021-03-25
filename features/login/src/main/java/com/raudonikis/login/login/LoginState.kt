package com.raudonikis.login.login

sealed class LoginState {
    object Disabled: LoginState()
    object Enabled: LoginState()
}
