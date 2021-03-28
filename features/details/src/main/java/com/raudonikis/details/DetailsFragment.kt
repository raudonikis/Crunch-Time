package com.raudonikis.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.disable
import com.raudonikis.common.extensions.enable
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.details.databinding.FragmentDetailsBinding
import com.raudonikis.details.game_review.ReviewDialogFragment
import com.raudonikis.details.game_screenshot.ScreenshotItem
import com.raudonikis.details.game_status.GameStatusSelectDialogFragment
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding: FragmentDetailsBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    /**
     * Screenshots
     */
    private val screenshotItemAdapter = ItemAdapter<ScreenshotItem>()
    private val screenshotAdapter = FastAdapter.with(screenshotItemAdapter)

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
     * Set up
     */

    private fun setUpViews() {
        with(binding) {
            recyclerScreenshots.adapter = screenshotAdapter
        }
    }

    private fun setUpObservers() {
        viewModel.detailsState
            .onEach { updateDetailsState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.currentGame
            .onEach { game ->
                binding.bindGame(requireContext(), game, screenshotItemAdapter)
            }
            .observeInLifecycle(viewLifecycleOwner)
    }

    private fun setUpListeners() {
        binding.apply {
            buttonChangeStatus.setOnClickListener {
                GameStatusSelectDialogFragment().apply {
                    setGameStatus(viewModel.currentGame.value.status)
                    setOnUpdateClicked { viewModel.updateGameStatus(it) }
                }.show(parentFragmentManager, GameStatusSelectDialogFragment.TAG_GAME_STATUS_UPDATE)
            }
            buttonDeals.setOnClickListener {
                viewModel.onDealsButtonClicked()
            }
            buttonReviews.setOnClickListener {
                viewModel.onReviewsButtonClicked()
            }
        }
    }

    /**
     * Details
     */
    private fun updateDetailsState(detailsState: DetailsState) {
        when (detailsState) {
            is DetailsState.StatusUpdateNotNeeded -> {
                binding.progressGameStatus.hide()
                binding.buttonChangeStatus.enable()
            }
            is DetailsState.StatusUpdating -> {
                binding.progressGameStatus.show()
                binding.buttonChangeStatus.disable()
            }
            is DetailsState.StatusUpdateSuccess -> {
                binding.progressGameStatus.hide()
                binding.buttonChangeStatus.enable()
                Toast.makeText(requireContext(), "Status successfully updated", Toast.LENGTH_LONG)
                    .show()
            }
            is DetailsState.StatusUpdateFailure -> {
                binding.progressGameStatus.hide()
                binding.buttonChangeStatus.enable()
                Toast.makeText(requireContext(), "Status update failed", Toast.LENGTH_LONG).show()
            }
            is DetailsState.DetailsUpdating -> {
                binding.progressUpdatingDetails.show()
            }
            is DetailsState.DetailsUpdateSuccess -> {
                binding.progressUpdatingDetails.hide()
            }
            is DetailsState.DetailsUpdateFailure -> {
                binding.progressUpdatingDetails.hide()
            }
        }
    }
}