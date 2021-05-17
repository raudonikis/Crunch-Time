package com.raudonikis.data_domain.game.models

import java.util.*

enum class GameCollectionType {
    PLAYED,
    PLAYING,
    WANT;

    override fun toString(): String {
        return name.toLowerCase(Locale.ROOT)
    }
}