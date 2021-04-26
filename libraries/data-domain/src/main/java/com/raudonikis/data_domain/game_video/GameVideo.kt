package com.raudonikis.data_domain.game_video

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameVideo(
    val url: String,
) : Parcelable