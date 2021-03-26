package com.raudonikis.login.login

sealed class LoginState {
    object Initial: LoginState()
    object Loading: LoginState()
}
