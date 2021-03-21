package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.*
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.common_ui.databinding.ItemGameBinding
import com.raudonikis.common_ui.extensions.hideKeyboard
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.game_item.GameItem
import com.raudonikis.common_ui.game_item.GameItemMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.testGames
import com.raudonikis.discover.databinding.FragmentDiscoverBinding
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
                setOnClearClick {
                    viewModel.onSearchQueryCleared()
                }
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
            .debounce(1000)
            .onEach {
                hideKeyboard()
                viewModel.search(it)
            }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.discoverState
            .onEach { updateDiscoverState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.popularGamesState
            .onEach { updatePopularGamesState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.gameSearchState
            .onEach { updateSearchState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    private fun updatePopularGamesState(outcome: Outcome<List<Game>>) {
        outcome
            .onSuccess {
                popularGamesItemAdapter.update(GameItemMapper.fromGameList(it))
            }
            .onFailure {
                Toast.makeText(requireContext(), "Popular games failed", Toast.LENGTH_LONG).show()
            }
            .onEmpty {
                Toast.makeText(requireContext(), "Popular games empty", Toast.LENGTH_LONG).show()
            }
            .onLoading {
                Toast.makeText(requireContext(), "Popular games loading", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateSearchState(outcome: Outcome<List<Game>>) {
        outcome
            .onSuccess {
                binding.groupSearchLoading.hide()
                searchResultsAdapter.submitList(it)
            }
            .onFailure {
                searchResultsAdapter.submitList(emptyList())
                binding.groupSearchLoading.hide()
                Toast.makeText(requireContext(), "Search failed", Toast.LENGTH_LONG).show()
            }
            .onEmpty {
                searchResultsAdapter.submitList(emptyList())
                binding.groupSearchLoading.hide()
            }
            .onLoading {
                searchResultsAdapter.submitList(emptyList())
                binding.groupSearchLoading.show()
            }
    }

    private fun updateDiscoverState(state: DiscoverState) {
        when (state) {
            is DiscoverState.Discover -> {
                binding.groupDiscover.show()
                binding.groupSearch.hide()
                binding.groupSearchLoading.hide()
            }
            is DiscoverState.Search -> {
                binding.groupDiscover.hide()
                binding.groupSearch.show()
            }
        }
    }
}