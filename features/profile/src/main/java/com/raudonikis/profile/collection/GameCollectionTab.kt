package com.raudonikis.profile.collection

import com.raudonikis.data_domain.game.models.GameCollectionType

object GameCollectionTab {
    const val TAB_PLAYED = 0
    const val TAB_PLAYING = 1
    const val TAB_WANT = 2

    fun fromGameCollectionType(gameCollectionType: GameCollectionType): Int {
        return when (gameCollectionType) {
            GameCollectionType.PLAYED -> TAB_PLAYED
            GameCollectionType.PLAYING -> TAB_PLAYING
            GameCollectionType.WANT -> TAB_WANT
        }
    }
}