package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.Outcome
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.extensions.*
import com.raudonikis.common_ui.game_cover_item.GameCoverItem
import com.raudonikis.common_ui.game_cover_item.GameCoverItemMapper
import com.raudonikis.common_ui.game_item.GameItem
import com.raudonikis.common_ui.game_item.GameItemMapper
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.data_domain.testGames
import com.raudonikis.discover.databinding.FragmentDiscoverBinding
import com.raudonikis.discover.popular_games.PopularGamesState
import com.raudonikis.discover.search.GameSearchState
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()
    private val binding: FragmentDiscoverBinding by viewBinding()

    /**
     * Popular games
     */
    private val popularGamesItemAdapter = ItemAdapter<GameCoverItem>()
    private val popularGamesAdapter = FastAdapter.with(popularGamesItemAdapter)

    /**
     * Trending games
     */
    private val trendingGamesItemAdapter = ItemAdapter<GameCoverItem>()
    private val trendingGamesAdapter = FastAdapter.with(trendingGamesItemAdapter)

    /**
     * Game search results
     */
    private val gameSearchItemAdapter = ItemAdapter<GameItem>()
    private val gameSearchAdapter = FastAdapter.with(gameSearchItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpGameSearch()
        setUpGameLists()
        setUpObservers()
    }


    private fun setUpObservers() {
        binding.layoutHeader.getSearchComponent().asFlow()
            .debounce(SEARCH_DEBOUNCE_TIME)
            .onEach {
                hideKeyboard()
                viewModel.search(it)
            }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.gameSearchState
            .onEach { updateSearchState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.popularGamesState
            .onEach { updatePopularGamesState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Game search
     */
    private fun setUpGameSearch() {
        binding.apply {
            recyclerGameSearch.adapter = gameSearchAdapter
            recyclerGameSearch.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
            gameSearchAdapter.onClick {
                viewModel.onGameClicked(it.game)
            }
            layoutHeader.getSearchComponent().apply {
                setSearchQuery(viewModel.searchQuery)
                setOnClearClick {
                    viewModel.onSearchQueryCleared()
                }
            }
        }
    }

    private fun updateSearchState(state: GameSearchState) {
        with(binding) {
            groupSearch.showIf { state is GameSearchState.GameSearch }
            if (state is GameSearchState.GameSearch) {
                groupSearchLoading.showIf { state.outcome is Outcome.Loading }
                groupSearchEmpty.showIf { state.outcome is Outcome.Empty }
                state.outcome.onSuccess {
                    gameSearchItemAdapter.update(GameItemMapper.fromGameList(it))
                }
                    .onFailure {
                        gameSearchItemAdapter.clear()
                        showLongSnackbar("Search failed")
                    }
                    .onEmpty {
                        gameSearchItemAdapter.clear()
                    }
            }
        }
    }

    /**
     * Game lists
     */
    private fun setUpGameLists() {
        binding.apply {
            popularGames.setAdapter(popularGamesAdapter)
            popularGamesAdapter.onClick {
                viewModel.onGameClicked(it.game)
            }
            trendingGames.setAdapter(trendingGamesAdapter)
            trendingGamesAdapter.onClick {
                viewModel.onGameClicked(it.game)
            }
            val gameItems = GameCoverItemMapper.fromGameList(testGames)
            trendingGamesItemAdapter.update(gameItems)
        }
    }

    private fun updatePopularGamesState(state: PopularGamesState) {
        with(binding) {
            popularGames.showIf { state is PopularGamesState.PopularGames }
            trendingGames.showIf { state is PopularGamesState.PopularGames }
            if (state is PopularGamesState.PopularGames) {
                state.outcome.onSuccess {
                    popularGamesItemAdapter.update(GameCoverItemMapper.fromGameList(it))
                }
                    .onFailure {
                        showLongSnackbar("Failed to load popular games")
                    }
                    .onEmpty {
                    }
                    .onLoading {
                    }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_TIME: Long = 1200
    }
}