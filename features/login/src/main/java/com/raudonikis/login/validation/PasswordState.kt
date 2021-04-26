package com.raudonikis.login.validation

sealed class PasswordState : ValidationState() {
    object Initial : PasswordState()
    object Blank : PasswordState()
    data class TooShort(val password: String) : PasswordState()
    data class NotMatching(val password: String) : PasswordState()
    data class Valid(val password: String) : PasswordState()

    override val validState: Class<out ValidationState>
        get() = Valid::class.java

    fun getCurrentPassword(): String {
        return when (this) {
            is TooShort -> this.password
            is NotMatching -> this.password
            is Valid -> this.password
            else -> ""
        }
    }
}
