package com.raudonikis.network.game

import com.raudonikis.network.game_review.GameReviewResponse
import com.squareup.moshi.Json

data class GameAttributesResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "game_status")
    val gameStatus: String,
    @Json(name = "first_release_date")
    val releaseDate: String?,
    @Json(name = "review")
    val review: GameReviewResponse?,
)
/**
"attributes": {
"id": 132038,
"aggregated_rating": 75.5,
"category": 0,
"first_release_date": "2021-03-02T00:00:00.000000Z",
"involved_companies": [
95223,
95224
],
"name": "Maquette",
"summary": "MAQUETTE is a first-person recursive puzzle game that takes you into a world where every building, plant, and object are simultaneously tiny and staggeringly huge.",
"game_status": "empty"
},*/