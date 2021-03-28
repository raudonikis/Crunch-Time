package com.raudonikis.details

import com.raudonikis.data_domain.game.models.Game

object DetailsRouter {

    fun fromDetailsToDeals(game: Game) =
        DetailsFragmentDirections.actionDetailsFragmentToDealsFragment(game)

    fun fromDetailsToReviews(game: Game) =
        DetailsFragmentDirections.actionDetailsFragmentToReviewsFragment(game)
}