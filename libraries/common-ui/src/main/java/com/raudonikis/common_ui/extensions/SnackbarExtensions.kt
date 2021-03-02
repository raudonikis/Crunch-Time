package com.raudonikis.common_ui.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showShortSnackbar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showShortSnackbar(@StringRes resId: Int) {
    Snackbar.make(requireView(), resId, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showLongSnackbar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showLongSnackbar(@StringRes resId: Int) {
    Snackbar.make(requireView(), resId, Snackbar.LENGTH_LONG).show()
}