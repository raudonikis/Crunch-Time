package com.raudonikis.login.login

sealed class LoginEvent {
    object Loading: LoginEvent()
    object LoginSuccess: LoginEvent()
    object LoginFailure: LoginEvent()
    object InvalidInputs: LoginEvent()
}