package com.raudonikis.login.signup

sealed class SignUpEvent {
    object Success: SignUpEvent()
    object Failure: SignUpEvent()
}
