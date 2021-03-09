package com.raudonikis.data_domain.games.models

import com.raudonikis.network.game.GameStatusRequestBody
import com.squareup.moshi.Moshi

enum class GameStatus {
    PLAYED,
    WANT,
    PLAYING,
    EMPTY;

    override fun toString(): String {
        return when (this) {
            PLAYED -> KEY_PLAYED
            WANT -> KEY_WANT
            PLAYING -> KEY_PLAYING
            EMPTY -> KEY_EMPTY
        }
    }

    //todo: dont initialize new moshi every time
    fun toJson(): String {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(GameStatusRequestBody::class.java)
        val gameStatusRequestBody = GameStatusRequestBody(this.toString())
        return adapter.toJson(gameStatusRequestBody)
    }

    companion object {
        private const val KEY_PLAYED = "played"
        private const val KEY_WANT = "want"
        private const val KEY_PLAYING = "playing"
        private const val KEY_EMPTY = "empty"

        fun fromString(value: String?): GameStatus {
            return when (value) {
                KEY_PLAYED -> PLAYED
                KEY_WANT -> WANT
                KEY_PLAYING -> PLAYING
                else -> EMPTY
            }
        }
    }
}