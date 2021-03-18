package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.raudonikis.common.extensions.asFlow
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.common_ui.databinding.ItemGameBinding
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.discover.databinding.FragmentDiscoverBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()
    private val binding: FragmentDiscoverBinding by viewBinding()
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
        binding.textFieldSearch.editText.asFlow()
            .debounce(800)
            .onEach { viewModel.search(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.discoverState
            .onEach { discoverState ->
                when (discoverState) {
                    is DiscoverState.Initial -> {
                        binding.textNoResults.show()
                    }
                    is DiscoverState.Loading -> {
                        binding.progressBarSearch.show()
                        binding.textNoResults.hide()
                        searchResultsAdapter.submitList(emptyList())
                    }
                    is DiscoverState.SearchSuccess -> {
                        binding.progressBarSearch.hide()
                        binding.textNoResults.hide()
                        searchResultsAdapter.submitList(discoverState.results)
                    }
                    is DiscoverState.SearchFailure -> {
                        binding.progressBarSearch.hide()
                        binding.textNoResults.show()
                        searchResultsAdapter.submitList(emptyList())
                        binding.textNoResults.show()
                    }
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
    }
}