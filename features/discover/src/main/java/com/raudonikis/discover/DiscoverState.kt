package com.raudonikis.discover

sealed class DiscoverState {
    object Discover : DiscoverState()
    object Search : DiscoverState()
}