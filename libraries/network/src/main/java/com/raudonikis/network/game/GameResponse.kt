package com.raudonikis.network.game

import com.squareup.moshi.Json

data class GameResponse(
    @Json(name = "attributes")
    val attributes: GameAttributesResponse,
    @Json(name = "relations")
    val relations: GameRelationsResponse,
)

/**
 *
 * "identifier": 132038,
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
},
"relations": {
"cover": {
"id": 95903,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co21zz.jpg"
},
"genres": [
{
"id": 9,
"name": "Puzzle"
}
],
"platforms": [
{
"abbreviation": "PC",
"id": 6,
"name": "PC (Microsoft Windows)"
},
{
"abbreviation": "PS4",
"id": 48,
"name": "PlayStation 4"
},
{
"abbreviation": "PS5",
"id": 167,
"name": "PlayStation 5"
}
],
"screenshots": [
{
"id": 418563,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc8yyr.jpg"
},
{
"id": 418564,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc8yys.jpg"
},
{
"id": 418565,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc8yyt.jpg"
},
{
"id": 418566,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc8yyu.jpg"
},
{
"id": 418567,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big_2x/sc8yyv.jpg"
}
],
"videos": [
{
"id": 34859,
"name": "Announcement Trailer",
"url": "https://www.youtube.com/watch?v=H5mj4CP4_rI",
"video_id": "H5mj4CP4_rI"
}
]
}
 */
