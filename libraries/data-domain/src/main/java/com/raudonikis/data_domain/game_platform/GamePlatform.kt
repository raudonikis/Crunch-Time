package com.raudonikis.data_domain.game_platform

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GamePlatform(
    val name: String,
    val id: Long,
) : Parcelable