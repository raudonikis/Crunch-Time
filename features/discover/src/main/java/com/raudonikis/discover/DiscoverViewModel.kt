package com.raudonikis.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.repo.GamesRepository
import com.raudonikis.discover.popular_games.PopularGamesState
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
        MutableStateFlow(DiscoverState.Discover)
    private val _popularGamesState: MutableStateFlow<PopularGamesState> =
        MutableStateFlow(PopularGamesState.Initial)

    /**
     * Search data
     */
    var searchQuery: String = ""
        private set
    var searchJob: Job? = null

    /**
     * Observables
     */
    val discoverState: StateFlow<DiscoverState> = _discoverState
    val popularGamesState: StateFlow<PopularGamesState> = _popularGamesState

    init {
        updatePopularGames()
    }

    /**
     * Search
     */
    fun search(query: String) {
        searchQuery = query
        searchJob?.cancel()
        if (query.isBlank()) {
            _discoverState.value = DiscoverState.Discover
            return
        }
        _discoverState.value = DiscoverState.Searching
        searchJob = viewModelScope.launch(Dispatchers.IO) {
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
     * Popular/Trending games
     */
    fun updatePopularGames() {
        viewModelScope.launch(Dispatchers.IO) {
            gamesRepository.getPopularGames()
                .onSuccess {
                    _popularGamesState.value = PopularGamesState.Success(it)
                }
                .onFailure {
                    _popularGamesState.value = PopularGamesState.Failure
                }
                .onEmpty {
                    _popularGamesState.value = PopularGamesState.Failure
                }
        }
    }

    /**
     * Events
     */
    fun onSearchQueryCleared() {
        searchQuery = ""
        searchJob?.cancel()
        _discoverState.value = DiscoverState.Discover
    }

    /**
     * Navigation
     */
    fun navigateToDetails(game: Game) {
        navigationDispatcher.navigate(DiscoverRouter.discoverToDetails(game))
    }
}