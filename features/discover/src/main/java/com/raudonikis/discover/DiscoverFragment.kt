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
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.common_ui.databinding.ItemGameBinding
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.game_cover.GameItem
import com.raudonikis.common_ui.game_cover.GameCoverItemMapper
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
            search.setSearchQuery(viewModel.searchQuery)
            search.setOnClearClick { viewModel.onSearchQueryCleared() }
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
            val gameItems = GameCoverItemMapper.fromGameList(testGames)
            popularGamesItemAdapter.update(gameItems)
            trendingGamesItemAdapter.update(gameItems)
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        binding.search.asFlow()
            .debounce(800)
            .onEach { viewModel.search(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.discoverState
            .onEach { discoverState ->
                when (discoverState) {
                    is DiscoverState.Discover -> {
                        binding.apply {
                            popularGames.show()
                            trendingGames.show()
                            recyclerSearchResults.hide()
                            loadingSearch.hide()
                            loadingSearch.cancelAnimation()
                            textLoading.hide()
                        }
                    }
                    is DiscoverState.Searching -> {
                        binding.apply {
                            popularGames.hide()
                            trendingGames.hide()
                            recyclerSearchResults.hide()
                            textLoading.show()
                            loadingSearch.show()
                            loadingSearch.playAnimation()
                        }
                    }
                    is DiscoverState.SearchSuccess -> {
                        binding.apply {
                            popularGames.hide()
                            trendingGames.hide()
                            recyclerSearchResults.show()
                            loadingSearch.hide()
                            loadingSearch.cancelAnimation()
                            textLoading.hide()
                        }
                        searchResultsAdapter.submitList(discoverState.results)
                    }
                    is DiscoverState.SearchFailure -> {
                        binding.apply {
                            popularGames.show()
                            trendingGames.show()
                            recyclerSearchResults.hide()
                            loadingSearch.hide()
                            loadingSearch.cancelAnimation()
                            textLoading.hide()
                        }
                        searchResultsAdapter.submitList(emptyList())
                        Toast.makeText(requireContext(), "Search failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
    }
}