package com.raudonikis.data_domain.game_review

import android.os.Parcelable
import com.raudonikis.data_domain.game.models.Game
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameReview(
    val gameId: Long,
    val gameTitle: String = "",
    val gameCoverUrl: String? = null,
    val content: String = "",
    val isPositive: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val userId: String? = null,
) : Parcelable {

    fun addGameInfo(game: Game): GameReview {
        return copy(
            gameTitle = game.name,
            gameCoverUrl = game.coverUrl
        )
    }
}
