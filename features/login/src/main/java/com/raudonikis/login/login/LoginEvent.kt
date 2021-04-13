package com.raudonikis.login.login

import com.raudonikis.data_domain.user.User

sealed class LoginEvent {
    data class InitialiseFields(val user: User) : LoginEvent()
    object LoginSuccess : LoginEvent()
    data class LoginFailure(val errorMessage: String?) : LoginEvent()
    object LoginResponseEmpty : LoginEvent()
}