package com.raudonikis.activity.user_activity_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.activity.R
import com.raudonikis.activity.databinding.ItemUserActivityBinding
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.extensions.getRatingDrawable
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game_rating.GameRating

class UserActivityItem(val userActivity: UserActivity) :
    AbstractBindingItem<ItemUserActivityBinding>() {

    override val type: Int
        get() = R.id.adapter_activity_id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemUserActivityBinding {
        return ItemUserActivityBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemUserActivityBinding, payloads: List<Any>) {
        with(binding) {
            bindGameCover(userActivity.coverUrl)
            when (val action = userActivity.action) {
                is UserActivityAction.ActionGameStatusUpdated -> {
                    iconRating.hide()
                    gameStatus.show()
                    textAction.text = "Updated"
                    textGameTitle.text = action.title
                    gameStatus.setGameStatus(action.status)
                }
                is UserActivityAction.ActionGameRated -> {
                    gameStatus.hide()
                    iconRating.show()
                    textAction.text = "Ranked"
                    textGameTitle.text = action.title
                    bindGameRating(action.rating)
                }
            }
        }
    }

    private fun ItemUserActivityBinding.bindGameRating(gameRating: GameRating) {
        Glide
            .with(root)
            .load(gameRating.getRatingDrawable())
            .into(iconRating)
    }

    private fun ItemUserActivityBinding.bindGameCover(coverUrl: String?) {
        coverUrl?.let { url ->
            Glide
                .with(root)
                .load(url.prefixHttps())
                .placeholder(R.drawable.game_placeholder)
                .centerCrop()
                .into(imageGameCover)
        }
    }
}