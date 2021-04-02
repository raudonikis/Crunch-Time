package com.raudonikis.common_ui.game_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.ItemGameBinding
import com.raudonikis.data_domain.game.models.Game

class GameItem(val game: Game) : AbstractBindingItem<ItemGameBinding>() {

    override val type: Int
        get() = R.id.adapter_game_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemGameBinding {
        return ItemGameBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemGameBinding, payloads: List<Any>) {
        with(binding) {
            gameTitle.text = game.name
            gameDescription.text = game.description
            game.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
                    .centerCrop()
                    .into(gameCover)
            }
        }
    }
}