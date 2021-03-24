package com.raudonikis.login.validation

abstract class ValidationState {

    abstract val validState: Class<out ValidationState>

    inline fun onValid(f: () -> Unit): ValidationState {
        if (this.javaClass == validState) {
            f()
        }
        return this
    }

    inline fun onInvalid(f: () -> Unit): ValidationState {
        if (this.javaClass != validState) {
            f()
        }
        return this
    }
}