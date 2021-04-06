package com.raudonikis.network.game_review

import com.raudonikis.network.auth.UserResponse
import com.squareup.moshi.Json

data class GameReviewResponse(
    @Json(name = "game_id")
    val gameId: Long,
    @Json(name = "content")
    val content: String? = null,
    @Json(name = "positive")
    val isPositive: Int,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "user")
    val user: UserResponse,
)
