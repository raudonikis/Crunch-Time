package com.raudonikis.profile.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.profile.R
import com.raudonikis.profile.databinding.ItemGameCoverBinding

class GameCoverItem(val game: Game) : AbstractBindingItem<ItemGameCoverBinding>() {

    override val type: Int
        get() = R.id.adapter_game_cover_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemGameCoverBinding {
        return ItemGameCoverBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemGameCoverBinding, payloads: List<Any>) {
        with(binding) {
            game.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
                    .centerCrop()
                    .into(imageGameCover)
            }
        }
    }
}