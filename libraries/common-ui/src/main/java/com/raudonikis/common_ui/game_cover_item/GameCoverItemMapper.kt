package com.raudonikis.common_ui.game_cover_item

import com.raudonikis.data_domain.game.models.Game

object GameCoverItemMapper {

    /**
     * From [Game] to [GameCoverItem]
     */
    private fun fromGame(game: Game): GameCoverItem {
        return GameCoverItem(game)
    }

    /**
     * From [Game] list to [GameCoverItem] list
     */
    fun fromGameList(games: List<Game>): List<GameCoverItem> {
        return games.map { fromGame(it) }
    }
}