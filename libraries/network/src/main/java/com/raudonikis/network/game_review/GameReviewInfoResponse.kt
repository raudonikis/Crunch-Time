package com.raudonikis.network.game_review

import com.squareup.moshi.Json

data class GameReviewInfoResponse(
    @Json(name = "positiveCount")
    val positiveCount: Int,
    @Json(name = "negativeCount")
    val negativeCount: Int,
    @Json(name = "reviews")
    val reviews: List<GameReviewResponse> = listOf(),
)
