package com.raudonikis.details.game_review

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.date.DateFormatter
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.show
import com.raudonikis.data_domain.game_review.GameReview
import com.raudonikis.details.R
import com.raudonikis.details.databinding.ItemReviewBinding
import java.util.*

data class ReviewItem(val review: GameReview) : AbstractBindingItem<ItemReviewBinding>() {

    override val type: Int
        get() = R.id.adapter_review_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemReviewBinding {
        return ItemReviewBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemReviewBinding, payloads: List<Any>) {
        with(binding) {
            textGameTitle.text = review.gameTitle
            textUserName.text = review.userId.toString()
            textReviewContent.text = review.content
            bindCreationDate(review)
            bindCoverImage(review)
            bindGameRating(review)
        }
    }

    private fun ItemReviewBinding.bindCreationDate(review: GameReview) {
        review.createdAt?.let { date ->
            textDate.text = DateFormatter.dateToFormattedString(date)
        }
    }

    private fun ItemReviewBinding.bindCoverImage(review: GameReview) {
        imageGameCover.setImageDrawable(null)
        review.gameCoverUrl?.let { coverUrl ->
            Glide
                .with(root)
                .load(coverUrl.prefixHttps())
                .centerCrop()
                .into(imageGameCover)
            textImagePlaceholder.hide()
        } ?: kotlin.run {
            textImagePlaceholder.text = review.gameTitle
            textImagePlaceholder.show()
        }
    }

    private fun ItemReviewBinding.bindGameRating(review: GameReview) {
        val ratingDrawable = when (review.isPositive) {
            true -> R.drawable.ic_like
            else -> R.drawable.ic_dislike
        }
        imageRating.setImageResource(ratingDrawable)
    }
}
