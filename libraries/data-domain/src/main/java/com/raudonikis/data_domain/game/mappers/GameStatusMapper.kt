package com.raudonikis.data_domain.game.mappers

import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.network.game_status.GameStatusRequestBody

object GameStatusMapper {

    /**
     * From [GameStatus] to [GameStatusRequestBody]
     */
    fun toGameStatusRequestBody(gameStatus: GameStatus): GameStatusRequestBody {
        return GameStatusRequestBody(gameStatus.toString())
    }
}