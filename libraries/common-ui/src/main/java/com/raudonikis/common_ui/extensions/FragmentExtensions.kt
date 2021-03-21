package com.raudonikis.common_ui.extensions

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    getSystemService(requireContext(), InputMethodManager::class.java)?.toggleSoftInput(
        InputMethodManager.SHOW_IMPLICIT,
        0
    )
}

fun Fragment.showKeyboard() {
    getSystemService(requireContext(), InputMethodManager::class.java)?.toggleSoftInput(
        InputMethodManager.HIDE_IMPLICIT_ONLY,
        0
    )
}