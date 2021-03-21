package com.raudonikis.common_ui.game_item

import com.raudonikis.data_domain.game.models.Game

object GameItemMapper {

    /**
     * From [Game] to [GameItem]
     */
    private fun fromGame(game: Game): GameItem {
        return GameItem(game)
    }

    /**
     * From [Game] list to [GameItem] list
     */
    fun fromGameList(games: List<Game>): List<GameItem> {
        return games.map { fromGame(it) }
    }
}