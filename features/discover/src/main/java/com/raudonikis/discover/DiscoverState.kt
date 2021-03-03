package com.raudonikis.discover

import com.raudonikis.data_domain.games.models.GameModel

sealed class DiscoverState {
    object Initial : DiscoverState()
    object Loading : DiscoverState()
    data class SearchSuccess(val results: List<GameModel>) : DiscoverState()
    object SearchFailure : DiscoverState()
}