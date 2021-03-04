package com.raudonikis.data_domain.games.models

enum class GameStatus {
    STATUS_PLAYED,
    STATUS_WANT,
    STATUS_PLAYING,
    STATUS_EMPTY;

    override fun toString(): String {
        return when (this) {
            STATUS_PLAYED -> "played"
            STATUS_WANT -> "want"
            STATUS_PLAYING -> "playing"
            STATUS_EMPTY -> "empty"
        }
    }
}