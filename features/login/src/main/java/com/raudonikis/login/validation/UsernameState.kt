package com.raudonikis.login.validation

sealed class UsernameState : ValidationState() {
    object Initial : UsernameState()
    object Blank : UsernameState()
    data class Valid(val username: String) : UsernameState()

    override val validState: Class<out ValidationState>
        get() = Valid::class.java

    fun getCurrentUsername(): String {
        return when(this) {
            is Valid -> this.username
            else -> ""
        }
    }
}
