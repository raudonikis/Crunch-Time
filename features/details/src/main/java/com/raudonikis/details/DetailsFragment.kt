package com.raudonikis.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common_ui.observeInLifecycle
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.details.databinding.FragmentDetailsBinding
import com.raudonikis.details.screenshots.ScreenshotItem
import com.raudonikis.details.screenshots.mappers.ScreenshotItemMapper
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding: FragmentDetailsBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private val screenshotAdapter = ItemAdapter<ScreenshotItem>()

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
     * Initialisation
     */

    private fun setUpViews() {
        with(binding) {
            recyclerScreenshots.apply {
                adapter = FastAdapter.with(screenshotAdapter)
            }
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.detailsState
            .onEach { updateDetailsState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.currentGame
            .onEach { updateGameDetails(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        binding.apply {
            /*buttonWant.setOnClickListener {
                viewModel.updateGameStatus(args.game, GameStatus.WANT)
            }
            buttonPlayed.setOnClickListener {
                viewModel.updateGameStatus(args.game, GameStatus.PLAYED)
            }
            buttonPlaying.setOnClickListener {
                viewModel.updateGameStatus(args.game, GameStatus.PLAYING)
            }*/
        }
    }

    /**
     * Details functionality
     */
    private fun updateGameDetails(game: Game) {
        binding.apply {
            labelTitle.text = game.name
//            gameStatus.text = game.status.toString()
            game.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
                    .centerCrop()
                    .into(imageCover)
            }
            if (game.screenshots.isNotEmpty()) {
                Glide
                    .with(root)
                    .load(game.screenshots.first().url.prefixHttps())
                    .centerCrop()
                    .into(imageWallpaper)
            }
            screenshotAdapter.clear()
            screenshotAdapter.add(ScreenshotItemMapper.fromScreenshotList(game.screenshots))
        }
    }

    private fun updateDetailsState(detailsState: DetailsState) {
        when (detailsState) {
            /*is DetailsState.StatusUpdating -> {
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
            }*/
            is DetailsState.DetailsUpdating -> {
            }
            is DetailsState.DetailsUpdateSuccess -> {
            }
            is DetailsState.DetailsUpdateFailure -> {
            }
        }
    }
}