package com.raudonikis.network.search

import com.raudonikis.network.game.GameCoverResponse
import com.raudonikis.network.game.GameScreenshotResponse
import com.squareup.moshi.Json

data class GameSearchResponse(
    /*@Json(name = "category")
    val category: Int,
    @Json(name = "collection")
    val collection: Int,*/
    @Json(name = "cover")
    val cover: GameCoverResponse?,
    @Json(name = "first_release_date")
    val releaseDate: String?,
    @Json(name = "aggregated_rating")
    val rating: Double?,
    @Json(name = "screenshots")
    val screenshots: List<GameScreenshotResponse>,
    /*@Json(name = "genres")
    val genres: List<Genre>,*/
    @Json(name = "id")
    val id: Long,
//    val involvedCompanies: List<InvolvedCompany>,
    @Json(name = "name")
    val name: String,
//    val platforms: List<Platform>,
    @Json(name = "summary")
    val summary: String?,
    @Json(name = "game_status")
    val gameStatus: String?,
)

/**
"cover": {
"height": 1906,
"id": 89271,
"url": "//images.igdb.com/igdb/image/upload/t_thumb/co1wvr.jpg",
"width": 1430
},
"genres": [
14
],
"involved_companies": [
6484,
6485,
6486
],
"name": "Mario & Sonic at the Olympic Winter Games",
"platforms": [
5
],
"summary": "Mario & Sonic at the Olympic Winter Games is a sports game with the official license of the 2010 Winter Olympic Games and a crossover franchise from the Mario and the Sonic universe similar to Mario & Sonic at the Olympic Games. It takes place at the Vancouver Winter Olympics of 2010. Like the previous game, the 27 events are presented as motion-based mini-games. Aside from the main events there are a series of Dream Events that have enhanced features and visuals. Some of the events can optionally be played using the balance board peripheral. Events include for instance skiing, snowboarding, bobsleigh, ice hockey, figure skating and more. The games can be played individually or in a festival mode that spans 17 days."
},
{
"category": 0,
"collection": 530,
"cover": {
"height": 800,
"id": 97072,
"url": "//images.igdb.com/igdb/image/upload/t_thumb/co22wg.jpg",
"width": 600
},
"first_release_date": "2009-10-13T00:00:00.000000Z",
"genres": [
14
],
"id": 132109,
"involved_companies": [
96037,
96038,
96039
],
"name": "Mario & Sonic at the Olympic Winter Games",
"platforms": [
20
],
"summary": "The DS-exclusive adventure mode features a story in which Dr. Eggman and Bowser melt away all the ice needed for the Olympic Games. To set things right Mario & Sonic need to travel to different locations, talk to the inhabitants and solve challenges. Those consist of the sports mini games with certain winning requirements. Playing this mode also unlocks the dream events. Besides this players can decide to enter the party game mode or to play the games separately respectively in a custom tournament. All modes except the adventure mode are also available for up to four players."
}*/