package com.raudonikis.details.game_review.mappers

import com.raudonikis.data_domain.game_review.GameReview
import com.raudonikis.details.game_review.ReviewItem

object ReviewItemMapper {

    /**
     * From [GameReview] to [ReviewItem]
     */
    fun fromGameReview(review: GameReview): ReviewItem {
        return ReviewItem(review)
    }

    /**
     * From a list of [GameReview] to a list of [ReviewItem]
     */
    fun fromGameReviewList(reviews: List<GameReview>): List<ReviewItem> {
        return reviews.map { fromGameReview(it) }
    }
}