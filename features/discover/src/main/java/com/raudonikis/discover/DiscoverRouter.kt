package com.raudonikis.discover

import com.raudonikis.data_domain.games.models.Game

object DiscoverRouter {
    fun discoverToDetails(game: Game) = DiscoverFragmentDirections.actionDiscoverFragmentToNavigationDetails(game)
}