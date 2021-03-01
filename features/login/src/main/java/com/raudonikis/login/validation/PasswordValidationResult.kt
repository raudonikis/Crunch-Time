package com.raudonikis.login.validation

enum class PasswordValidationResult {
    PASSWORD_INITIAL,
    PASSWORD_BLANK,
    PASSWORD_TOO_SHORT,
    PASSWORDS_NOT_MATCHING,
    PASSWORD_VALID;

    fun isValid(): Boolean = this == PASSWORD_VALID
}