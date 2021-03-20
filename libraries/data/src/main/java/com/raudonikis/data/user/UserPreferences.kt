package com.raudonikis.data.user

import android.content.SharedPreferences
import com.raudonikis.common.extensions.get
import com.raudonikis.common.extensions.put
import javax.inject.Inject

class UserPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var accessToken: String
        get() = sharedPreferences.get(KEY_ACCESS_TOKEN, "")
        set(value) {
            sharedPreferences.put(KEY_ACCESS_TOKEN, value)
        }

    var isRememberMeChecked: Boolean
        get() = sharedPreferences.get(KEY_REMEMBER_ME, false)
        set(value) {
            sharedPreferences.put(KEY_REMEMBER_ME, value)
        }

    var userEmail: String
        get() = sharedPreferences.get(KEY_USER_EMAIL, "")
        set(value) {
            sharedPreferences.put(KEY_USER_EMAIL, value)
        }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_REMEMBER_ME = "remember_me"
    }
}