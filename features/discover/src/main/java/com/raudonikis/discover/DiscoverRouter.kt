package com.raudonikis.discover

import com.raudonikis.data_domain.games.models.GameModel

object DiscoverRouter {
    fun discoverToDetails(game: GameModel) = DiscoverFragmentDirections.actionDiscoverFragmentToNavigationDetails(game)
}