package com.raudonikis.discover

sealed class DiscoverState {
    object Initial : DiscoverState()
    object Loading : DiscoverState()
    data class SearchSuccess(val results: List<String>) : DiscoverState()
    object SearchFailure : DiscoverState()
}