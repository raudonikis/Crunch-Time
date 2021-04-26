package com.raudonikis.data_domain.game_screenshot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameScreenshot(
    val url: String
) : Parcelable
