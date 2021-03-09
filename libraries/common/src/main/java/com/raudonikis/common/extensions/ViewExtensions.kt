package com.raudonikis.common.extensions

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.toggleVisibility() {
    when (visibility) {
        View.VISIBLE -> this.hide()
        else -> this.show()
    }
}

fun View.disable() {
    alpha = .3f
    isClickable = false
}

fun View.enable() {
    alpha = 1f
    isClickable = true
}

fun View.showIf(condition: () -> Boolean) {
    when (condition()) {
        true -> show()
        false -> hide()
    }
}