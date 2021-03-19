package com.raudonikis.data_domain.game_video.mappers

import com.raudonikis.data_domain.game_video.GameVideo
import com.raudonikis.network.game_video.GameVideoResponse

object GameVideoMapper {

    fun fromGameVideoResponse(response: GameVideoResponse): GameVideo {
        return GameVideo(response.url)
    }

    fun fromGameVideResponseList(responses: List<GameVideoResponse>?): List<GameVideo> {
        return responses?.map { fromGameVideoResponse(it) } ?: emptyList()
    }
}