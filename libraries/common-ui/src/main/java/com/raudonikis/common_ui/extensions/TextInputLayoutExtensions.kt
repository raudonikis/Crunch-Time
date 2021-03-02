package com.raudonikis.common_ui.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.clearError() {
    this.error = ""
}

var TextInputLayout.text: String
    get() = this.editText?.text.toString()
    set(value) {
        this.editText?.setText(value)
    }