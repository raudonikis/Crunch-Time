package com.raudonikis.discover.popular_games

import com.raudonikis.data_domain.game.models.Game

sealed class PopularGamesState {
    object Initial : PopularGamesState()
    object Loading : PopularGamesState()
    data class Success(val games: List<Game>) : PopularGamesState()
    object Failure: PopularGamesState()
}
