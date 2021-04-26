package com.raudonikis.data_domain.game_genre.mappers

import com.raudonikis.data_domain.game_genre.GameGenre
import com.raudonikis.network.game_genre.GameGenreResponse

object GameGenreMapper {

    /**
     * To [GameGenre]
     */
    fun fromGameGenreResponse(gameGenreResponse: GameGenreResponse): GameGenre {
        return GameGenre(gameGenreResponse.name)
    }

    fun fromGameGenreResponseList(gameGenreResponses: List<GameGenreResponse>): List<GameGenre> {
        return gameGenreResponses.map { fromGameGenreResponse(it) }
    }
}