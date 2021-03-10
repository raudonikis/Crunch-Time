package com.raudonikis.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.repo.GamesRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _discoverState: MutableStateFlow<DiscoverState> =
        MutableStateFlow(DiscoverState.Initial)
    var searchQuery: String = ""
        private set

    /**
     * Observables
     */
    val discoverState: StateFlow<DiscoverState> = _discoverState

    /**
     * Search
     */
    fun search(query: String) {
        searchQuery = query
        if (query.isBlank()) {
            _discoverState.value = DiscoverState.SearchSuccess(emptyList())
            return
        }
        _discoverState.value = DiscoverState.Loading
        viewModelScope.launch {
            gamesRepository.search(query)
                .onSuccess {
                    _discoverState.value = DiscoverState.SearchSuccess(it)
                }
                .onEmpty {
                    _discoverState.value = DiscoverState.SearchFailure
                }
                .onFailure {
                    _discoverState.value = DiscoverState.SearchFailure
                }
        }
    }

    /**
     * Navigation
     */
    fun navigateToDetails(game: Game) {
        navigationDispatcher.navigate(DiscoverRouter.discoverToDetails(game))
    }
}