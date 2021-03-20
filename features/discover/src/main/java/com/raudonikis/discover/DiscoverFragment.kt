package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.show
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.common_ui.databinding.ItemGameBinding
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.game_cover.GameItem
import com.raudonikis.common_ui.game_cover.GameItemMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.testGames
import com.raudonikis.discover.databinding.FragmentDiscoverBinding
import com.raudonikis.discover.popular_games.PopularGamesState
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
    private val popularGamesItemAdapter = ItemAdapter<GameItem>()
    private val popularGamesAdapter = FastAdapter.with(popularGamesItemAdapter)

    /**
     * Trending games
     */
    private val trendingGamesItemAdapter = ItemAdapter<GameItem>()
    private val trendingGamesAdapter = FastAdapter.with(trendingGamesItemAdapter)

    /**
     * Search results
     */
    private val searchResultsAdapter = RecyclerAdapter<Game, ItemGameBinding>(
        onInflate = { inflater, parent ->
            ItemGameBinding.inflate(inflater, parent, false)
        },
        onBind = { game ->
            this.gameTitle.text = game.name
            this.gameDescription.text = game.description
            game.coverUrl?.let { url ->
                Glide
                    .with(this.root)
                    .load(url.prefixHttps())
                    .centerCrop()
                    .into(this.gameCover)
            }
        },
        onClick = { viewModel.navigateToDetails(this) }
    )

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearch()
        setUpGameLists()
        setUpObservers()
    }

    private fun setUpSearch() {
        binding.apply {
            recyclerSearchResults.adapter = searchResultsAdapter
            layoutHeader.getSearchComponent().apply {
                setSearchQuery(viewModel.searchQuery)
                setOnClearClick { viewModel.onSearchQueryCleared() }
            }
        }
    }

    private fun setUpGameLists() {
        binding.apply {
            popularGames.setAdapter(popularGamesAdapter)
            popularGamesAdapter.onClick {
                viewModel.navigateToDetails(it.game)
            }
            trendingGames.setAdapter(trendingGamesAdapter)
            trendingGamesAdapter.onClick {
                viewModel.navigateToDetails(it.game)
            }
            val gameItems = GameItemMapper.fromGameList(testGames)
            trendingGamesItemAdapter.update(gameItems)
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        binding.layoutHeader.getSearchComponent().asFlow()
            .debounce(800)
            .onEach { viewModel.search(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.discoverState
            .onEach { updateDiscoverState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.popularGamesState
            .onEach { updatePopularGamesState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    private fun updatePopularGamesState(state: PopularGamesState) {
        when (state) {
            is PopularGamesState.Success -> {
                popularGamesItemAdapter.update(GameItemMapper.fromGameList(state.games))
            }
            is PopularGamesState.Failure -> {
                Toast.makeText(requireContext(), "popular games failed", Toast.LENGTH_SHORT).show()
            }
            is PopularGamesState.Loading -> {
                Toast.makeText(requireContext(), "popular games loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateDiscoverState(state: DiscoverState) {
        when (state) {
            is DiscoverState.Discover -> {
                updateGameListsVisibility(true)
                updateLoadingVisibility(false)
                updateSearchVisibility(false)
            }
            is DiscoverState.Searching -> {
                updateGameListsVisibility(false)
                updateLoadingVisibility(true)
                updateSearchVisibility(false)
            }
            is DiscoverState.SearchSuccess -> {
                updateGameListsVisibility(false)
                updateLoadingVisibility(false)
                updateSearchVisibility(true)
                searchResultsAdapter.submitList(state.results)
            }
            is DiscoverState.SearchFailure -> {
                updateGameListsVisibility(true)
                updateLoadingVisibility(false)
                updateSearchVisibility(false)
                searchResultsAdapter.submitList(emptyList())
                Toast.makeText(requireContext(), "Search failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Update the visibility of every game list
     * GameLists affected -> [popularGamesAdapter], [trendingGamesAdapter]
     */
    private fun updateGameListsVisibility(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                popularGames.show()
                trendingGames.show()
            } else {
                popularGames.hide()
                trendingGames.hide()
            }
        }
    }

    /**
     * Update the visibility of loading indicators
     */
    private fun updateLoadingVisibility(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                textLoading.show()
                loadingSearch.show()
                loadingSearch.playAnimation()
            } else {
                loadingSearch.hide()
                loadingSearch.cancelAnimation()
                textLoading.hide()
            }
        }
    }

    /**
     * Update the visibility of search results
     */
    private fun updateSearchVisibility(isShown: Boolean) {
        binding.apply {
            recyclerSearchResults.showIf { isShown }
        }
    }
}