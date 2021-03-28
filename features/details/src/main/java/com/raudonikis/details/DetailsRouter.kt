package com.raudonikis.details

import com.raudonikis.data_domain.game_review.GameReview

object DetailsRouter {

    fun fromDetailsToDeals() = DetailsFragmentDirections.actionDetailsFragmentToDealsFragment()
    fun fromDetailsToReviews(reviews: List<GameReview>, gameId: Long) =
        DetailsFragmentDirections.actionDetailsFragmentToReviewsFragment(
            reviews = reviews.toTypedArray(),
            gameId = gameId
        )
}