package com.raudonikis.network.game_review

import com.squareup.moshi.Json

data class GameReviewRequestBody(
    @Json(name = "title")
    val title: String = "title",
    @Json(name = "content")
    val content: String,
    @Json(name = "positive")
    val isPositive: Boolean,
    @Json(name = "game_id")
    val gameId: Long,
)
