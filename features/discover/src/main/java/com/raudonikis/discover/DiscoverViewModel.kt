package com.raudonikis.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.games.repo.GamesRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    /**
     * States
     */
    private val discoverState: MutableStateFlow<DiscoverState> =
        MutableStateFlow(DiscoverState.Initial)
    var searchQuery: String = ""
        private set

    /**
     * Observables
     */
    fun discoverStateObservable() = discoverState.asLiveData(viewModelScope.coroutineContext)

    /**
     * Search
     */
    fun search(query: String) {
        searchQuery = query
        discoverState.value = DiscoverState.Loading
        viewModelScope.launch {
            gamesRepository.search(query)
                .onSuccess {
                    discoverState.value = DiscoverState.SearchSuccess(it.map { it.name })
                }
                .onEmpty {
                    discoverState.value = DiscoverState.SearchFailure
                }
                .onFailure {
                    discoverState.value = DiscoverState.SearchFailure
                }
        }
    }

    /**
     * Navigation
     */
}