package com.raudonikis.data_domain.games.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val coverUrl: String? = null,
    val status: GameStatus = GameStatus.EMPTY,
    val isUpdateNeeded: Boolean = false,
) : Parcelable