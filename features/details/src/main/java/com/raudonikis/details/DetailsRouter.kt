package com.raudonikis.details

import com.raudonikis.data_domain.game.models.Game

object DetailsRouter {

    fun fromDetailsToDeals() = DetailsFragmentDirections.actionDetailsFragmentToDealsFragment()
    fun fromDetailsToReviews(game: Game) =
        DetailsFragmentDirections.actionDetailsFragmentToReviewsFragment(game)
}