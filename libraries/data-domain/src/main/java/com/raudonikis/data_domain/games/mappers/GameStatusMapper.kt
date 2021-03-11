package com.raudonikis.data_domain.games.mappers

import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.network.game.GameStatusRequestBody
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
}