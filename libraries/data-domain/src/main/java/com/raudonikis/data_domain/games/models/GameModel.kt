package com.raudonikis.data_domain.games.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameModel(
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverUrl: String?,
) : Parcelable