package com.raudonikis.data_domain

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus

val testGames = listOf(
    Game(
        name = "Game 1",
        description = "Game 1 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        status = GameStatus.PLAYING,
    ),
    Game(
        name = "Game 2",
        description = "Game 2 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        status = GameStatus.PLAYED,
    ),
    Game(
        name = "Game 3",
        description = "Game 3 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        status = GameStatus.WANT,
    ),
    Game(
        name = "Game 4",
        description = "Game 4 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        status = GameStatus.PLAYING,
    ),
    Game(
        name = "Game 5",
        description = "Game 5 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        status = GameStatus.WANT,
    ),
    Game(
        name = "Game 6",
        description = "Game 6 description",
        coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
        status = GameStatus.EMPTY,
    ),
)