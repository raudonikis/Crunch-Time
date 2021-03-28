package com.raudonikis.network.game_review

import com.squareup.moshi.Json

data class GameReviewPostResponse(
    @Json(name = "game_id")
    val gameId: Long,
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
