package com.raudonikis.profile.collection.mappers

import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.profile.collection.GameCollectionTab

object GameCollectionTypeMapper {

    /**
     * From [GameCollectionTab] to [GameCollectionType]
     */
    fun fromGameCollectionTab(position: Int): GameCollectionType? {
        return when (position) {
            GameCollectionTab.TAB_PLAYING -> GameCollectionType.PLAYING
            GameCollectionTab.TAB_PLAYED -> GameCollectionType.PLAYED
            GameCollectionTab.TAB_WANT -> GameCollectionType.WANT
            else -> null
        }
    }
}