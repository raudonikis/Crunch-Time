package com.raudonikis.common_ui.game_cover_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.ItemGameCoverBinding
import com.raudonikis.data_domain.game.models.Game

class GameCoverItem(val game: Game) : AbstractBindingItem<ItemGameCoverBinding>() {

    override val type: Int
        get() = R.id.adapter_game_cover_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemGameCoverBinding {
        return ItemGameCoverBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemGameCoverBinding, payloads: List<Any>) {
        with(binding) {
            imageGameCover.setImageDrawable(null)
            textImagePlaceholder.text = game.name
            textImagePlaceholder.showIf { game.coverUrl == null }
            game.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
                    .centerCrop()
                    .into(imageGameCover)
            }
            gameStatus.setGameStatus(game.status)
        }
    }
}