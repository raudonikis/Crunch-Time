package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.raudonikis.common.extensions.*
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.data_domain.games.models.GameModel
import com.raudonikis.discover.databinding.FragmentDiscoverBinding
import com.raudonikis.discover.databinding.ItemGameBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()
    private val binding: FragmentDiscoverBinding by viewBinding()
    private val searchResultsAdapter = RecyclerAdapter<GameModel, ItemGameBinding>(
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
        setUpObservers()
    }

    private fun setUpSearch() {
        binding.apply {
            textFieldSearch.editText?.setText(viewModel.searchQuery)
            recyclerSearchResults.adapter = searchResultsAdapter
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        lifecycleScope.launch {
            binding.textFieldSearch.editText.asFlow()
                .debounce(800)
                .collect { viewModel.search(it) }
        }
        viewModel.discoverStateObservable().observe(viewLifecycleOwner) { discoverState ->
            when (discoverState) {
                is DiscoverState.Loading -> {
                    binding.progressBarSearch.show()
                    searchResultsAdapter.submitList(emptyList())
                }
                is DiscoverState.SearchSuccess -> {
                    binding.progressBarSearch.hide()
                    searchResultsAdapter.submitList(discoverState.results)
                }
                is DiscoverState.SearchFailure -> {
                    binding.progressBarSearch.hide()
                    searchResultsAdapter.submitList(emptyList())
                }
            }
        }
    }
}