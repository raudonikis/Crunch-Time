package com.raudonikis.profile.collection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.profile.R
import com.raudonikis.profile.databinding.FragmentGameCollectionBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GameCollectionFragment : Fragment(R.layout.fragment_game_collection) {

    private val viewModel: GameCollectionViewModel by viewModels()
    private val binding: FragmentGameCollectionBinding by viewBinding()
    private val args: GameCollectionFragmentArgs by navArgs()

    /*private val gameCollectionAdapter = RecyclerAdapter<Game, ItemGameBinding>(
        onInflate = { inflater, parent ->
            ItemGameBinding.inflate(inflater, parent, false)
        },
        onBind = { game ->
            gameTitle.text = game.name
            gameDescription.text = game.description
            game.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
                    .centerCrop()
                    .into(gameCover)
            }
        },
        onClick = { viewModel.onGameClick(this) }
    )*/

    /**
     * Lifecycle hooks
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(args.gameStatus)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpViews()
    }

    /**
     * Set up
     */
    private fun setUpViews() {
        with(binding) {
//            recyclerGameCollection.adapter = gameCollectionAdapter
            titleCollection.text = args.gameStatus.toString().capitalize()
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.gameCollectionState
            .onEach { state ->
                when (state) {
                    is GameCollectionState.Initial -> {
                    }
                    is GameCollectionState.Loading -> {
                        binding.progressCollection.show()
                    }
                    is GameCollectionState.Success -> {
                        binding.progressCollection.hide()
//                        gameCollectionAdapter.submitList(state.games)
                        with(binding) {
                            titleCollection.text = args.gameStatus.toString().capitalize()
                            collectionSize.text = "${state.games.size} games"
                        }
                    }
                    is GameCollectionState.Failure -> {
                        binding.progressCollection.hide()
                    }
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
    }
}