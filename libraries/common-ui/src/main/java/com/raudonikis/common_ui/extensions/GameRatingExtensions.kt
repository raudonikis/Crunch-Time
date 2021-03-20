package com.raudonikis.common_ui.extensions

import androidx.annotation.DrawableRes
import com.raudonikis.common_ui.R
import com.raudonikis.data_domain.game_rating.GameRating

@DrawableRes
fun GameRating.getRatingDrawable(): Int {
    return when (this) {
        GameRating.UP_VOTED -> R.drawable.ic_like
        GameRating.DOWN_VOTED -> R.drawable.ic_dislike
    }
}