package com.raudonikis.data_domain.game_status

import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.network.game_status.GameStatusRequestBody

object GameStatusMapper {

    /**
     * From [GameStatus] to [GameStatusRequestBody]
     */
    fun toGameStatusRequestBody(gameStatus: GameStatus): GameStatusRequestBody {
        return GameStatusRequestBody(gameStatus.toString())
    }

    fun fromGameCollectionType(gameCollectionType: GameCollectionType): GameStatus {
        return when (gameCollectionType) {
            GameCollectionType.PLAYING -> GameStatus.PLAYING
            GameCollectionType.PLAYED -> GameStatus.PLAYED
            GameCollectionType.WANT -> GameStatus.WANT
        }
    }
}