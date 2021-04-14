package com.raudonikis.login.signup

sealed class SignUpEvent {
    object Success : SignUpEvent()
    data class Failure(val errorMessage: String?) : SignUpEvent()
    object Empty : SignUpEvent()
}
