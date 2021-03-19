package com.raudonikis.discover

import com.raudonikis.data_domain.game.models.Game

sealed class DiscoverState {
    object Discover : DiscoverState()
    object Searching : DiscoverState()
    data class SearchSuccess(val results: List<Game>) : DiscoverState()
    object SearchFailure : DiscoverState()
}