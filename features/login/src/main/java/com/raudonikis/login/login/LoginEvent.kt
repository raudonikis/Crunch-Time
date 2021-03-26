package com.raudonikis.login.login

sealed class LoginEvent {
    data class InitialiseFields(val email: String) : LoginEvent()
    object LoginSuccess : LoginEvent()
    object LoginFailure : LoginEvent()
}