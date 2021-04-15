package com.raudonikis.data_domain.game_platform

import com.raudonikis.network.game_platform.GamePlatformResponse

object GamePlatformMapper {

    fun fromGamePlatformResponse(gamePlatformResponse: GamePlatformResponse): GamePlatform {
        return GamePlatform(name = gamePlatformResponse.name, id = gamePlatformResponse.id)
    }

    fun fromGamePlatformResponseList(gamePlatformResponses: List<GamePlatformResponse>): List<GamePlatform> {
        return gamePlatformResponses.map { fromGamePlatformResponse(it) }
    }
}