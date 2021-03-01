package com.raudonikis.login.validation

object ValidationUtils {

    private const val REGEX_EMAIL = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"

    fun validateEmail(email: String?): EmailValidationResult {
        return when {
            email.isNullOrBlank() -> {
                EmailValidationResult.EMAIL_BLANK
            }
            email.matches(REGEX_EMAIL.toRegex()) -> {
                EmailValidationResult.EMAIL_VALID
            }
            else -> {
                EmailValidationResult.EMAIL_BAD_FORMAT
            }
        }
    }

    fun validatePassword(password: String?): PasswordValidationResult {
        return when {
            password.isNullOrBlank() -> PasswordValidationResult.PASSWORD_BLANK
            else -> PasswordValidationResult.PASSWORD_VALID
        }
    }

    fun validatePasswordWithConfirmation(password: String, confirmationPassword: String) {
        //todo
    }
}