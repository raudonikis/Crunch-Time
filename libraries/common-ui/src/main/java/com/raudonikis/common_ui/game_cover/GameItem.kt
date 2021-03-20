package com.raudonikis.common_ui.game_cover

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.ItemGameCoverBinding
import com.raudonikis.data_domain.game.models.Game

class GameItem(val game: Game) : AbstractBindingItem<ItemGameCoverBinding>() {

    override val type: Int
        get() = R.id.adapter_game_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemGameCoverBinding {
        return ItemGameCoverBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemGameCoverBinding, payloads: List<Any>) {
        with(binding) {
            imageGameCover.setImageResource(R.drawable.game_placeholder)
            game.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
                    .placeholder(R.drawable.game_placeholder)
                    .centerCrop()
                    .into(imageGameCover)
            }
            gameStatus.setGameStatus(game.status)
        }
    }
}