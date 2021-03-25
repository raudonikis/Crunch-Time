package com.raudonikis.login.signup

sealed class SignUpState {
    object Disabled : SignUpState()
    object Enabled : SignUpState()
}
