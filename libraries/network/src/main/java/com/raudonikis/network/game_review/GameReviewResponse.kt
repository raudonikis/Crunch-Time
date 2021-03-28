package com.raudonikis.network.game_review

import com.squareup.moshi.Json

data class GameReviewResponse(
    @Json(name = "game_id")
    val gameId: Long,
    @Json(name = "content")
    val content: String = "",
    @Json(name = "positive")
    val isPositive: Int,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "user_id")
    val userId: String? = null,
)
/**
 * "uuid": "92d76731-bb83-434e-be77-560f363f5bcf",
"game_id": 2020,
"content": "Content 2",
"positive": 1,
"created_at": "2021-02-28T18:54:29.000000Z",
"updated_at": "2021-02-28T18:55:02.000000Z",
"title": "Game review",
"user_id": null
 */
