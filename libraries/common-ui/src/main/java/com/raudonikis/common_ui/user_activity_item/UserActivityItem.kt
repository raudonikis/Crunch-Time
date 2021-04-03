package com.raudonikis.common_ui.user_activity_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.date.DateFormatter
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.ItemUserActivityBinding
import com.raudonikis.common_ui.extensions.getRatingDrawable
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.models.UserActivityAction
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_status.GameStatus
import java.util.*

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
            bindCreationDate(userActivity.createdAt)
            bindAction(userActivity.action)
        }
    }

    private fun ItemUserActivityBinding.bindGameRating(gameRating: GameRating) {
        Glide
            .with(root)
            .load(gameRating.getRatingDrawable())
            .into(iconRating)
    }

    private fun ItemUserActivityBinding.bindGameStatus(status: GameStatus) {
        gameStatus.setGameStatus(status)
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

    private fun ItemUserActivityBinding.bindCreationDate(createdAt: Date?) {
        createdAt?.let {
            textDate.text = DateFormatter.dateToFormattedString(createdAt)
        }
    }

    private fun ItemUserActivityBinding.bindAction(action: UserActivityAction) {
        iconRating.showIf { action is UserActivityAction.ActionGameRated }
        gameStatus.showIf { action is UserActivityAction.ActionGameStatusUpdated }
        when (action) {
            is UserActivityAction.ActionGameStatusUpdated -> {
                bindActionText("Updated")//todo
                bindGameTitle(action.title)
                bindGameStatus(action.status)
                bindUser(action.user)
            }
            is UserActivityAction.ActionGameRated -> {
                bindActionText("Ranked")//todo
                bindGameTitle(action.title)
                bindGameRating(action.rating)
                bindUser(action.user)
            }
            is UserActivityAction.ActionListCreated -> {
                bindUser(action.user)
            }
        }
    }

    private fun ItemUserActivityBinding.bindUser(user: String) {
        textUser.text = user
    }

    private fun ItemUserActivityBinding.bindActionText(actionText: String) {
        textAction.text = actionText
    }

    private fun ItemUserActivityBinding.bindGameTitle(title: String) {
        textGameTitle.text = title
    }
}