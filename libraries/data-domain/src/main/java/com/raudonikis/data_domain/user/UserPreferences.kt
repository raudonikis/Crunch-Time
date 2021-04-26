package com.raudonikis.data_domain.user

import android.content.SharedPreferences
import com.raudonikis.common.extensions.get
import com.raudonikis.common.extensions.put
import com.squareup.moshi.Moshi
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi,
) {

    var isRememberMeChecked: Boolean
        get() = sharedPreferences.get(KEY_REMEMBER_ME, false)
        set(value) {
            sharedPreferences.put(KEY_REMEMBER_ME, value)
        }

    var currentUser: User?
        get() {
            val jsonAdapter = moshi.adapter(User::class.java)
            sharedPreferences.get(KEY_CURRENT_USER, "").also {
                return jsonAdapter.fromJson(it)
            }
        }
        set(value) {
            val jsonAdapter = moshi.adapter(User::class.java)
            sharedPreferences.put(KEY_CURRENT_USER, jsonAdapter.toJson(value))
        }

    companion object {
        private const val KEY_REMEMBER_ME = "remember_me"
        private const val KEY_CURRENT_USER = "current_user"
    }
}