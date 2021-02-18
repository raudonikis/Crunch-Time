package com.raudonikis.discover

import androidx.lifecycle.ViewModel
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    var searchQuery = ""
        private set

    fun search(query: String) {
        searchQuery = query
    }
}