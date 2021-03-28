package com.raudonikis.details.game_review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.details.R
import com.raudonikis.details.databinding.FragmentReviewsBinding
import com.raudonikis.details.game_review.mappers.ReviewItemMapper
import com.wada811.viewbinding.viewBinding

class ReviewsFragment : Fragment(R.layout.fragment_reviews) {

    private val binding: FragmentReviewsBinding by viewBinding()
    private val args: ReviewsFragmentArgs by navArgs()

    /**
     * Reviews
     */
    private val reviewsItemAdapter = ItemAdapter<ReviewItem>()
    private val reviewsAdapter = FastAdapter.with(reviewsItemAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpReviews()
    }

    private fun setUpReviews() {
        with(binding) {
            recyclerReviews.adapter = reviewsAdapter
            recyclerReviews.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_normal
                )
            )
            val reviews = ReviewItemMapper.fromGameReviewList(args.reviews.toList())
            reviewsItemAdapter.update(reviews)
            textReviewCount.text = reviews.size.toString() + " reviews"
        }
    }
}