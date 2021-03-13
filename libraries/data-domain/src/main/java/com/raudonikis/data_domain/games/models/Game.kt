package com.raudonikis.data_domain.games.models

import android.os.Parcelable
import com.raudonikis.data_domain.screenshots.Screenshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val coverUrl: String? = null,
    val screenshots: List<Screenshot> = listOf(),
    val status: GameStatus = GameStatus.EMPTY,
    val isUpdateNeeded: Boolean = false,
) : Parcelable