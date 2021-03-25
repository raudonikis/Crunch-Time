package com.raudonikis.login.validation

sealed class EmailState : ValidationState() {
    object Initial : EmailState()
    object Blank : EmailState()
    data class BadFormat(val email: String) : EmailState()
    data class Valid(val email: String) : EmailState()

    override val validState: Class<out ValidationState>
        get() = Valid::class.java

    fun getCurrentEmail(): String {
        return when (this) {
            is BadFormat -> this.email
            is Valid -> this.email
            else -> ""
        }
    }
}
