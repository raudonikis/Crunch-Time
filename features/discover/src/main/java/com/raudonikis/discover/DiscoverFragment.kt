package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.asFlow
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.common_ui.databinding.ItemGameBinding
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.game_cover.GameItem
import com.raudonikis.common_ui.game_cover.GameCoverItemMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
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
        binding.apply {/*
            textFieldSearch.editText?.setText(viewModel.searchQuery)
            recyclerSearchResults.adapter = searchResultsAdapter*/
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
            val games = listOf(
                Game(
                    name = "Game 1",
                    description = "Game 1 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                    status = GameStatus.PLAYING,
                ),
                Game(
                    name = "Game 2",
                    description = "Game 2 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                    status = GameStatus.PLAYED,
                ),
                Game(
                    name = "Game 3",
                    description = "Game 3 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                    status = GameStatus.WANT,
                ),
                Game(
                    name = "Game 4",
                    description = "Game 4 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                    status = GameStatus.PLAYING,
                ),
                Game(
                    name = "Game 5",
                    description = "Game 5 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                    status = GameStatus.WANT,
                ),
                Game(
                    name = "Game 6",
                    description = "Game 6 description",
                    coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                    status = GameStatus.EMPTY,
                ),
            )
            popularGamesItemAdapter.clear()
            popularGamesItemAdapter.add(GameCoverItemMapper.fromGameList(games))
            trendingGamesItemAdapter.clear()
            trendingGamesItemAdapter.add(GameCoverItemMapper.fromGameList(games))
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        /*binding.textFieldSearch.editText.asFlow()
            .debounce(800)
            .onEach { viewModel.search(it) }
            .observeInLifecycle(viewLifecycleOwner)*/
        viewModel.discoverState
            .onEach { discoverState ->
                when (discoverState) {
                    is DiscoverState.Initial -> {
//                        binding.textNoResults.show()
                    }
                    is DiscoverState.Loading -> {
                        binding.progressBarSearch.show()
//                        binding.textNoResults.hide()
                        searchResultsAdapter.submitList(emptyList())
                    }
                    is DiscoverState.SearchSuccess -> {
                        binding.progressBarSearch.hide()
//                        binding.textNoResults.hide()
                        searchResultsAdapter.submitList(discoverState.results)
                    }
                    is DiscoverState.SearchFailure -> {
                        binding.progressBarSearch.hide()
//                        binding.textNoResults.show()
                        searchResultsAdapter.submitList(emptyList())
                        binding.textNoResults.show()
                    }
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
    }
}