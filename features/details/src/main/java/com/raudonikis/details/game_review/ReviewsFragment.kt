package com.raudonikis.details.game_review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.showShortSnackbar
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.details.DetailsViewModel
import com.raudonikis.details.R
import com.raudonikis.details.databinding.FragmentReviewsBinding
import com.raudonikis.details.game_review.mappers.ReviewItemMapper
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ReviewsFragment : Fragment(R.layout.fragment_reviews) {

    private val viewModel: ReviewsViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by hiltNavGraphViewModels(R.id.navigation_details)
    private val binding: FragmentReviewsBinding by viewBinding()
    private val args: ReviewsFragmentArgs by navArgs()

    /**
     * Reviews
     */
    private val reviewsItemAdapter = ItemAdapter<ReviewItem>()
    private val reviewsAdapter = FastAdapter.with(reviewsItemAdapter)
    private var writeReviewDialog: WriteReviewDialogFragment? = null

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpReviews()
        setUpListeners()
        setUpObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(args.game)
    }

    /**
     * Set up
     */
    private fun setUpReviews() {
        with(binding) {
            recyclerReviews.adapter = reviewsAdapter
            recyclerReviews.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_normal
                )
            )
        }
    }

    private fun setUpObservers() {
        viewModel.writeReviewState
            .onEach { onReviewState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.currentGame
            .onEach { onGameUpdate(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    private fun setUpListeners() {
        with(binding) {
            textButtonWriteReview.setOnClickListener {
                writeReviewDialog = WriteReviewDialogFragment()
                    .setOnSaveClicked { gameRating, comment ->
                        viewModel.postReview(gameRating, comment)
                    }.also {
                        it.show(parentFragmentManager, WriteReviewDialogFragment.TAG_REVIEW)
                    }
            }
        }
    }

    /**
     * Events
     */
    private fun onReviewState(state: ReviewState) {
        writeReviewDialog?.setReviewState(state)
        when (state) {
            ReviewState.SUCCESS -> {
                showShortSnackbar("Review posted successfully")
                writeReviewDialog?.dismiss()
                writeReviewDialog = null
            }
            ReviewState.FAILURE -> {
                showShortSnackbar("Failed to post review")
            }
        }
    }

    private fun onGameUpdate(game: Game) {
        detailsViewModel.onGameUpdated(game)
        val reviews = ReviewItemMapper.fromGameReviewList(game.gameReviewInfo.reviews)
        reviewsItemAdapter.update(reviews)
        binding.textReviewCount.text =
            getString(R.string.label_review_count, game.gameReviewInfo.reviews.size)
        binding.groupFailure.showIf { reviews.isEmpty() }
    }
}