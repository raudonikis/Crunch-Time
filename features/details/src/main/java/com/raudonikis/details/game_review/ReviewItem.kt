package com.raudonikis.details.game_review

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.data_domain.game_review.GameReview
import com.raudonikis.details.R
import com.raudonikis.details.databinding.ItemReviewBinding

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
            textDate.text = review.createdAt
            review.gameCoverUrl?.let { coverUrl ->
                Glide
                    .with(root)
                    .load(coverUrl.prefixHttps())
                    .centerCrop()
                    .into(imageGameCover)
            }
            val ratingDrawable = when (review.isPositive) {
                true -> R.drawable.ic_like
                else -> R.drawable.ic_dislike
            }
            imageRating.setImageResource(ratingDrawable)
        }
    }
}
