package com.raudonikis.network.game_review

import com.squareup.moshi.Json

data class GameReviewPostResponse(
    @Json(name = "game_id")
    val gameId: Long,
    @Json(name = "content")
    val content: String,
    @Json(name = "positive")
    val isPositive: Boolean,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "user_id")
    val userId: String? = null,
)
/**
 * "data": {
"game_id": 2,
"title": "Game review",
"content": "Content",
"positive": true,
"uuid": "930f78b2-01d9-47f5-a384-4a7c47b80251",
"user_id": 1,
"updated_at": "2021-03-28T15:49:47.000000Z",
"created_at": "2021-03-28T15:49:47.000000Z"
}
 */
