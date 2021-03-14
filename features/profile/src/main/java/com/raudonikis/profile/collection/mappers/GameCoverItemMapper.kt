package com.raudonikis.profile.collection.mappers

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.profile.collection.GameCoverItem

object GameCoverItemMapper {

    /**
     * From [Game] to [GameCoverItem]
     */
    fun fromGame(game: Game): GameCoverItem {
        return GameCoverItem(game)
    }

    /**
     * From [Game] list to [GameCoverItem] list
     */
    fun fromGameList(games: List<Game>): List<GameCoverItem> {
        return games.map { fromGame(it) }
    }
}