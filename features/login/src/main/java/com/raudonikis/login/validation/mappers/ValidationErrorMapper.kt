package com.raudonikis.login.validation.mappers

import com.raudonikis.core.providers.StringProvider
import com.raudonikis.login.R
import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.UsernameState
import javax.inject.Inject

class ValidationErrorMapper @Inject constructor(private val stringProvider: StringProvider) {

    fun fromEmailState(emailState: EmailState): String? {
        return when (emailState) {
            is EmailState.BadFormat -> stringProvider.getString(R.string.validation_error_email_bad_format)
            is EmailState.Blank -> stringProvider.getString(R.string.validation_error_email_blank)
            else -> null
        }
    }

    fun fromPasswordState(passwordState: PasswordState): String? {
        return when (passwordState) {
            is PasswordState.Blank -> stringProvider.getString(R.string.validation_error_password_blank)
            is PasswordState.TooShort -> stringProvider.getString(R.string.validation_error_password_too_short)
            is PasswordState.NotMatching -> stringProvider.getString(R.string.validation_error_password_not_matching)
            else -> null
        }
    }

    fun fromUsernameState(usernameState: UsernameState): String? {
        return when (usernameState) {
            is UsernameState.Blank -> stringProvider.getString(R.string.validation_error_username_blank)
            else -> null
        }
    }
}