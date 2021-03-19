package com.raudonikis.common_ui.game_cover

import com.raudonikis.data_domain.game.models.Game

object GameCoverItemMapper {

    /**
     * From [Game] to [GameItem]
     */
    fun fromGame(game: Game): GameItem {
        return GameItem(game)
    }

    /**
     * From [Game] list to [GameItem] list
     */
    fun fromGameList(games: List<Game>): List<GameItem> {
        return games.map { fromGame(it) }
    }
}