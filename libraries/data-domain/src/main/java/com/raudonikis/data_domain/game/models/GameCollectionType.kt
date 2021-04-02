package com.raudonikis.data_domain.game.models

import com.raudonikis.data_domain.game_status.GameStatus

enum class GameCollectionType {
    PLAYED,
    PLAYING,
    WANT;

    fun toGameStatus(): GameStatus {
        return when (this) {
            PLAYING -> GameStatus.PLAYING
            PLAYED -> GameStatus.PLAYED
            WANT -> GameStatus.WANT
        }
    }
}