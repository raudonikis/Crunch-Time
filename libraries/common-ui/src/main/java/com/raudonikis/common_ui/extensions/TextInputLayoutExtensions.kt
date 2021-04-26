package com.raudonikis.common_ui.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.clearError() {
    this.error = null
}

fun TextInputLayout.updateError(message: String?) {
    if (message.isNullOrBlank()) {
        this.isErrorEnabled = false
    } else {
        this.error = message
    }
}

var TextInputLayout.text: String
    get() = this.editText?.text.toString()
    set(value) {
        this.editText?.setText(value)
    }