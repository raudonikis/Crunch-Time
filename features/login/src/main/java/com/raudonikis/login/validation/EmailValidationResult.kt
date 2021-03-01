package com.raudonikis.login.validation

enum class EmailValidationResult {
    EMAIL_INITIAL,
    EMAIL_BLANK,
    EMAIL_BAD_FORMAT,
    EMAIL_VALID;

    fun isValid(): Boolean = this == EMAIL_VALID
}