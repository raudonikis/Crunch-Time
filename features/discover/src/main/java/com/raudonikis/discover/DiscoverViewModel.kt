package com.raudonikis.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_search.GameSearchUseCase
import com.raudonikis.data_domain.popular_games.PopularGamesUseCase
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    // Use cases
    private val popularGamesUseCase: PopularGamesUseCase,
    private val gameSearchUseCase: GameSearchUseCase,
) : ViewModel() {

    /**
     * States
     */
    private val _discoverState: MutableStateFlow<DiscoverState> =
        MutableStateFlow(DiscoverState.Discover)

    /**
     * Search data
     */
    var searchQuery: String = ""
        private set
    var searchJob: Job? = null

    /**
     * Observables
     */
    val discoverState: Flow<DiscoverState> = _discoverState
    val popularGamesState: Flow<Outcome<List<Game>>> = popularGamesUseCase.getPopularGames()
    val gameSearchState: Flow<Outcome<List<Game>>> = gameSearchUseCase.getGameSearchResults()

    init {
        updatePopularGames()
    }

    /**
     * Search
     */
    fun search(query: String) {
        searchQuery = query
        clearSearchJob()
        if (query.isBlank()) {
            _discoverState.value = DiscoverState.Discover
            gameSearchUseCase.clearSearchResults()
            return
        }
        _discoverState.value = DiscoverState.Search
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            gameSearchUseCase.search(query)
        }
    }

    private fun clearSearchJob() {
        searchJob?.cancel()
        searchJob = null
    }

    /**
     * Popular/Trending games
     */
    private fun updatePopularGames() {
        viewModelScope.launch(Dispatchers.IO) {
            popularGamesUseCase.updatePopularGames()
        }
    }

    /**
     * Events
     */
    fun onSearchQueryCleared() {
        searchQuery = ""
        clearSearchJob()
        gameSearchUseCase.clearSearchResults()
        _discoverState.value = DiscoverState.Discover
    }

    fun onGameClicked(game: Game) {
        navigateToDetails(game)
    }

    /**
     * Navigation
     */
    private fun navigateToDetails(game: Game) {
        navigationDispatcher.navigate(DiscoverRouter.discoverToDetails(game))
    }
}