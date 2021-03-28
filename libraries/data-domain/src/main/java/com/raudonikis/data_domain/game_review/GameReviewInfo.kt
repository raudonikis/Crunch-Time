package com.raudonikis.data_domain.game_review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameReviewInfo(
    val positiveCount: Int,
    val negativeCount: Int,
    val reviews: List<GameReview> = listOf(),
) : Parcelable
