package com.raudonikis.data_domain

import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.data_domain.game_deal.GameDealShop
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_video.GameVideo

val testGames = listOf(
    Game(
        name = "Game 1",
        description = "Game 1 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.PLAYING,
    ),
    Game(
        name = "Game 2",
        description = "Game 2 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.PLAYED,
    ),
)

val testGames2 = listOf(
    Game(
        name = "Game 3",
        description = "Game 3 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.WANT,
    ),
    Game(
        name = "Game 4",
        description = "Game 4 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.PLAYING,
    ),
    Game(
        name = "Game 5",
        description = "Game 5 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.WANT,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
)

val testGames3 = listOf(
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        videos = listOf(GameVideo("https://www.youtube.com/watch?v=H5mj4CP4_rI")),
        status = GameStatus.EMPTY,
    ),
)

val testDeals = listOf(
    GameDeal(
        59.90,
        69.90,
        null,
        0.0,
        GameDealShop("idk", "Steam")
    ),
    GameDeal(
        40.90,
        69.90,
        null,
        0.0,
        GameDealShop("idk", "Random")
    ),
    GameDeal(
        12.90,
        14.90,
        null,
        0.0,
        GameDealShop("idk", "Amazon")
    ),
    GameDeal(
        20.90,
        30.90,
        null,
        0.0,
        GameDealShop("idk", "Steam")
    ),
    GameDeal(
        13.90,
        20.90,
        null,
        0.0,
        GameDealShop("idk", "Amazon")
    ),
    GameDeal(
        40.90,
        42.90,
        null,
        0.0,
        GameDealShop("idk", "Steam")
    ),
)

/*
val testActivities = listOf(
    UserActivity(
        gameId = 1,
        action = UserActivityAction.ActionGameStatusUpdated(
            title = "Game 1",
            status = GameStatus.WANT,
            user = "NedasK",
        ),
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
    ),
    UserActivity(
        gameId = 2,
        action = UserActivityAction.ActionGameRated(
            title = "Game 2",
            rating = GameRating.DOWN_VOTED,
            user = "NedasK",
        ),
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
    ),
    UserActivity(
        gameId = 3,
        action = UserActivityAction.ActionGameRated(
            title = "Game 3",
            rating = GameRating.UP_VOTED,
            user = "NedasK",
        ),
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
    ),
    UserActivity(
        gameId = 4,
        action = UserActivityAction.ActionGameStatusUpdated(
            title = "Game 4",
            status = GameStatus.PLAYED,
            user = "NedasK",
        ),
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
    ),
    UserActivity(
        gameId = 5,
        action = UserActivityAction.ActionGameStatusUpdated(
            title = "Game 5",
            status = GameStatus.PLAYING,
            user = "NedasK",
        ),
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
    ),
)*/
