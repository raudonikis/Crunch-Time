package com.raudonikis.discover.popular_games

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game

sealed class PopularGamesState {
    object Disabled : PopularGamesState()
    data class PopularGames(val outcome: Outcome<List<Game>>) : PopularGamesState()
}
