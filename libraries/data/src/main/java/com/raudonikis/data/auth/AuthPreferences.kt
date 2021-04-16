package com.raudonikis.data.auth

import android.content.SharedPreferences
import com.auth0.jwt.JWT
import com.raudonikis.common.extensions.get
import com.raudonikis.common.extensions.put
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AuthPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var accessToken: String
        get() = sharedPreferences.get(KEY_ACCESS_TOKEN, "")
        set(value) {
            sharedPreferences.put(KEY_ACCESS_TOKEN, value)
        }

    fun isUserAuthenticated(): Boolean {
        return try {
            val jwt = JWT.decode(accessToken)
            when {
                jwt.expiresAt == null -> false
                jwt.expiresAt.after(Date()) -> true
                else -> false
            }
        } catch (e: Exception) {
            Timber.w("Could not decode the JWT token -> ${e.message}")
            false
        }
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}