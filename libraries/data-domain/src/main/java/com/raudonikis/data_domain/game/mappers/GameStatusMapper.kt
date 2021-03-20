package com.raudonikis.data_domain.game.mappers

import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.network.game_status.GameStatusRequestBody
import com.squareup.moshi.Moshi
import javax.inject.Inject

class GameStatusMapper @Inject constructor(private val moshi: Moshi) {

    /**
     * From [GameStatus]
     */
    fun toJson(gameStatus: GameStatus): String {
        val adapter = moshi.adapter(GameStatusRequestBody::class.java)
        val gameStatusRequestBody = GameStatusRequestBody(gameStatus.toString())
        return adapter.toJson(gameStatusRequestBody)
    }

    fun toGameStatusRequestBody(gameStatus: GameStatus): GameStatusRequestBody {
        return GameStatusRequestBody(gameStatus.toString())
    }
}