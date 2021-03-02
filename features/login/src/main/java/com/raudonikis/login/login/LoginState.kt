package com.raudonikis.login.login

sealed class LoginState {
    object Initial: LoginState()
    object Loading: LoginState()
    object LoginSuccess: LoginState()
    object LoginFailure: LoginState()
    object InvalidInputs: LoginState()
}