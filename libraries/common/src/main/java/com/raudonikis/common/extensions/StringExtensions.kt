package com.raudonikis.common.extensions

fun String.limit(characterCount: Int): String {
    return if (this.length > characterCount) {
        "${this.substring(0, characterCount)}..."
    } else {
        this
    }
}

fun String.prefixHttps(): String {
    return when {
        this.startsWith("http") -> {
            this
        }
        this.startsWith("/") -> {
            this.removePrefix("/").prefixHttps()
        }
        else -> {
            "https://$this"
        }
    }
}