package com.raudonikis.common.extensions

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.CoroutineContext

fun EditText?.asFlow() = callbackFlow {
    this@asFlow?.doOnTextChanged { text, _, _, _ ->
        offer(text.toString())
    }
    awaitClose()
}

fun EditText?.asLiveData(coroutineContext: CoroutineContext) = asFlow().asLiveData(coroutineContext)