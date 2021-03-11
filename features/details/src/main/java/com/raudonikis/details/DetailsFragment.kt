package com.raudonikis.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.raudonikis.common.extensions.disable
import com.raudonikis.common.extensions.enable
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common_ui.observeInLifecycle
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.details.databinding.FragmentDetailsBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding: FragmentDetailsBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    /**
     * Lifecycle hooks
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(args.game)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpListeners()
        setUpObservers()
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.detailsState
            .onEach { state ->
                when (state) {
                    is DetailsState.StatusUpdating -> {
                        binding.buttonPlayed.disable()
                        binding.buttonPlaying.disable()
                        binding.buttonWant.disable()
                    }
                    is DetailsState.StatusUpdateSuccess -> {
                        binding.buttonPlayed.enable()
                        binding.buttonPlaying.enable()
                        binding.buttonWant.enable()
                        Timber.d("Status update success")
                    }
                    is DetailsState.StatusUpdateFailure -> {
                        binding.buttonPlayed.enable()
                        binding.buttonPlaying.enable()
                        binding.buttonWant.enable()
                        Timber.d("Status update failure")
                    }
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.currentGame
            .onEach { updateGameDetails(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Initialisation
     */
    private fun setUpViews() {
        binding.apply {
            args.game.let { game ->
                gameTitle.text = game.name
                gameStatus.text = game.status.toString()
                game.coverUrl?.let { url ->
                    Glide
                        .with(this.root)
                        .load(url.prefixHttps())
                        .centerCrop()
                        .into(this.gameCover)
                }
            }
        }
    }

    private fun updateGameDetails(game: Game) {
        binding.apply {
            gameStatus.text = game.status.toString()
        }
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        binding.apply {
            buttonWant.setOnClickListener {
                viewModel.updateGameStatus(args.game, GameStatus.WANT)
            }
            buttonPlayed.setOnClickListener {
                viewModel.updateGameStatus(args.game, GameStatus.PLAYED)
            }
            buttonPlaying.setOnClickListener {
                viewModel.updateGameStatus(args.game, GameStatus.PLAYING)
            }
        }
    }
}