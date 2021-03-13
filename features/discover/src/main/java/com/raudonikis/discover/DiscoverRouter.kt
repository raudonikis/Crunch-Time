package com.raudonikis.discover

import com.raudonikis.data_domain.game.models.Game

object DiscoverRouter {
    fun discoverToDetails(game: Game) = DiscoverFragmentDirections.actionDiscoverFragmentToNavigationDetails(game)
}