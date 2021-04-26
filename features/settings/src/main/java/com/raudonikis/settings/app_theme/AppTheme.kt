package com.raudonikis.settings.app_theme

enum class AppTheme {
    LIGHT,
    DARK,
    DEFAULT;

    override fun toString(): String {
        return when (this) {
            LIGHT -> KEY_LIGHT
            DARK -> KEY_DARK
            DEFAULT -> KEY_DEFAULT
        }
    }

    fun toPosition(): Int {
        return when (this) {
            DEFAULT -> POSITION_DEFAULT
            DARK -> POSITION_DARK
            LIGHT -> POSITION_LIGHT
        }
    }

    companion object {
        private const val KEY_LIGHT = "light"
        private const val KEY_DARK = "dark"
        private const val KEY_DEFAULT = "default"

        private const val POSITION_DEFAULT = 0
        private const val POSITION_DARK = 1
        private const val POSITION_LIGHT = 2

        fun fromString(value: String): AppTheme {
            return when (value) {
                KEY_LIGHT -> LIGHT
                KEY_DARK -> DARK
                KEY_DEFAULT -> DEFAULT
                else -> DEFAULT
            }
        }

        fun fromPosition(position: Int): AppTheme {
            return when (position) {
                POSITION_DEFAULT -> DEFAULT
                POSITION_DARK -> DARK
                POSITION_LIGHT -> LIGHT
                else -> DEFAULT
            }
        }
    }
}