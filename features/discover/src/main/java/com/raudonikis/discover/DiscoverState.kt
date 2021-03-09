package com.raudonikis.discover

import com.raudonikis.data_domain.games.models.Game

sealed class DiscoverState {
    object Initial : DiscoverState()
    object Loading : DiscoverState()
    data class SearchSuccess(val results: List<Game>) : DiscoverState()
    object SearchFailure : DiscoverState()
}