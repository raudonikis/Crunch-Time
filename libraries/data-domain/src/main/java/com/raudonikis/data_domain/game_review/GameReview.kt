package com.raudonikis.data_domain.game_review

import android.os.Parcelable
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.user.User
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GameReview(
    val gameId: Long,
    val gameTitle: String = "",
    val gameCoverUrl: String? = null,
    val content: String? = null,
    val isPositive: Boolean,
    val createdAt: Date?,
    val updatedAt: String,
    val user: User,
) : Parcelable {

    fun addGameInfo(game: Game): GameReview {
        return copy(
            gameTitle = game.name,
            gameCoverUrl = game.coverUrl
        )
    }
}
