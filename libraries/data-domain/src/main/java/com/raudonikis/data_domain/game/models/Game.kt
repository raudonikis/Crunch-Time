package com.raudonikis.data_domain.game.models

import android.os.Parcelable
import com.raudonikis.data_domain.game_genre.GameGenre
import com.raudonikis.data_domain.game_platform.GamePlatform
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.game_screenshot.GameScreenshot
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_video.GameVideo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val coverUrl: String? = null,
    val screenshots: List<GameScreenshot> = listOf(),
    val videos: List<GameVideo> = listOf(),
    val status: GameStatus = GameStatus.EMPTY,
    val gameGenres: List<GameGenre> = listOf(),
    val gamePlatforms: List<GamePlatform> = listOf(),
    val releaseDate: String = "",
    val gameReviewInfo: GameReviewInfo = GameReviewInfo(),
    val isUpdateNeeded: Boolean = false,
) : Parcelable