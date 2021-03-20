package com.raudonikis.data_domain

import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
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

val testActivities = listOf(
    UserActivity(
        gameId = 1,
        action = UserActivityAction.ActionGameStatusUpdated(
            title = "Game 1",
            status = GameStatus.WANT,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        ),
    ),
    UserActivity(
        gameId = 2,
        action = UserActivityAction.ActionGameRanked(
            title = "Game 2",
            rating = GameRating.DOWN_VOTED,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        ),
    ),
    UserActivity(
        gameId = 3,
        action = UserActivityAction.ActionGameRanked(
            title = "Game 3",
            rating = GameRating.UP_VOTED,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        ),
    ),
    UserActivity(
        gameId = 4,
        action = UserActivityAction.ActionGameStatusUpdated(
            title = "Game 4",
            status = GameStatus.PLAYED,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        ),
    ),
    UserActivity(
        gameId = 5,
        action = UserActivityAction.ActionGameStatusUpdated(
            title = "Game 5",
            status = GameStatus.PLAYING,
            coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        ),
    ),
)