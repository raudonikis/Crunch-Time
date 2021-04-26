package com.raudonikis.data_domain.game_status

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameStatus : Parcelable {
    PLAYED,
    WANT,
    PLAYING,
    EMPTY;

    override fun toString(): String {
        return when (this) {
            PLAYED -> KEY_PLAYED
            WANT -> KEY_WANT
            PLAYING -> KEY_PLAYING
            EMPTY -> KEY_EMPTY
        }
    }

    companion object {
        private const val KEY_PLAYED = "played"
        private const val KEY_WANT = "want"
        private const val KEY_PLAYING = "playing"
        private const val KEY_EMPTY = "empty"

        fun fromString(value: String?): GameStatus {
            return when (value) {
                KEY_PLAYED -> PLAYED
                KEY_WANT -> WANT
                KEY_PLAYING -> PLAYING
                else -> EMPTY
            }
        }
    }
}