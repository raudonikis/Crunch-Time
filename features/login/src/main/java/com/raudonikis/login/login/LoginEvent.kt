package com.raudonikis.login.login

import com.raudonikis.data_domain.user.User

sealed class LoginEvent {
    data class InitialiseFields(val user: User) : LoginEvent()
    object LoginSuccess : LoginEvent()
    object LoginFailure : LoginEvent()
}