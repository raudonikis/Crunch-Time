package com.raudonikis.data.sharedpreferences

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

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}