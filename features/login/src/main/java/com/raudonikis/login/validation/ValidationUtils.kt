package com.raudonikis.login.validation

object ValidationUtils {

    private const val EMAIL_REGEX = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"
    private const val PASSWORD_MIN_LENGTH = 6

    fun validateEmail(email: String?): EmailState {
        return when {
            email.isNullOrBlank() -> {
                EmailState.Blank
            }
            email.matches(EMAIL_REGEX.toRegex()) -> {
                EmailState.Valid(email)
            }
            else -> {
                EmailState.BadFormat(email)
            }
        }
    }

    fun validatePassword(password: String?): PasswordState {
        return when {
            password.isNullOrBlank() -> PasswordState.Blank
            password.length < PASSWORD_MIN_LENGTH -> PasswordState.TooShort(password)
            else -> PasswordState.Valid(password)
        }
    }

    fun validateUsername(username: String?): UsernameState {
        return when {
            username.isNullOrBlank() -> UsernameState.Blank
            else -> UsernameState.Valid(username)
        }
    }

    fun validateConfirmPassword(
        password: String?,
        confirmPassword: String?
    ): PasswordState {
        return when {
            confirmPassword.isNullOrBlank() -> PasswordState.Blank
            confirmPassword.length < PASSWORD_MIN_LENGTH -> PasswordState.TooShort(confirmPassword)
            password != confirmPassword -> PasswordState.NotMatching(confirmPassword)
            else -> PasswordState.Valid(confirmPassword)
        }
    }
}