package com.raudonikis.common_ui.extensions

import android.content.SharedPreferences
import com.raudonikis.common.extensions.get
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

inline fun <reified T> SharedPreferences.observeKey(
    key: String,
    default: T
): Flow<T> {
    return callbackFlow {
        send(get(key, default))
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
            if (key == k) {
                offer(get(key, default))
            }
        }
        registerOnSharedPreferenceChangeListener(listener)
        awaitClose {
            unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
}