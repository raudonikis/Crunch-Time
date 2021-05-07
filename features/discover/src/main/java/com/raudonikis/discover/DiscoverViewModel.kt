package com.raudonikis.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_search.GameSearchUseCase
import com.raudonikis.data_domain.popular_games.PopularGamesUseCase
import com.raudonikis.discover.popular_games.PopularGamesState
import com.raudonikis.discover.search.GameSearchState
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
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
        MutableStateFlow(DiscoverState.DISCOVER)

    /**
     * Search data
     */
    var searchQuery: String = ""
        private set
    private var searchJob: Job? = null

    /**
     * Scroll states
     */
    var popularGamesScrollState: Int = 0

    /**
     * Observables
     */
    val popularGamesState: StateFlow<PopularGamesState> = popularGamesUseCase.getPopularGames()
        .combine(_discoverState) { popularGamesState, discoverState ->
            when (discoverState) {
                DiscoverState.DISCOVER -> {
                    PopularGamesState.PopularGames(popularGamesState)
                }
                else -> PopularGamesState.Disabled
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, PopularGamesState.Disabled)
    val gameSearchState: StateFlow<GameSearchState> = gameSearchUseCase.getGameSearchResults()
        .combine(_discoverState) { gameSearchState, discoverState ->
            when (discoverState) {
                DiscoverState.SEARCH -> {
                    GameSearchState.GameSearch(gameSearchState)
                }
                else -> GameSearchState.Disabled
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, GameSearchState.Disabled)

    init {
        updatePopularGames()
    }

    fun persistPopularGamesScrollState(popularGamesScrollState: Int) {
        this.popularGamesScrollState = popularGamesScrollState
    }

    /**
     * Search
     */
    fun search(query: String) {
        searchQuery = query
        clearSearchJob()
        if (query.isBlank()) {
            _discoverState.value = DiscoverState.DISCOVER
            gameSearchUseCase.clearSearchResults()
            return
        }
        _discoverState.value = DiscoverState.SEARCH
        searchJob = viewModelScope.launch {
            gameSearchUseCase.search(query)
        }
    }

    private fun clearSearchJob() {
        searchJob?.cancel()
        searchJob = null
    }

    /**
     * Popular games
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
        _discoverState.value = DiscoverState.DISCOVER
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