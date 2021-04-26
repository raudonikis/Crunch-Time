package com.raudonikis.data_domain.game_genre

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameGenre(
    val name: String,
) : Parcelable
