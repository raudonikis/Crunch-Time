package com.raudonikis.common_ui.extensions

import android.widget.RadioButton

fun RadioButton.check() {
    this.isChecked = true
}

fun RadioButton.uncheck() {
    this.isChecked = false
}