package com.raudonikis.common_ui.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.ItemGameCoverBinding
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_status.GameStatus

class GameItemLayout(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val binding: ItemGameCoverBinding

    init {
        val view = inflate(context, R.layout.item_game_cover, this)
        binding = ItemGameCoverBinding.bind(view)
    }

    fun bindGame(game: Game) {
        bindGameCover(game.coverUrl)
        bindGameStatus(game.status)
    }

    private fun bindGameStatus(gameStatus: GameStatus) {
        binding.gameStatus.setGameStatus(gameStatus)
    }

    private fun bindGameCover(coverUrl: String?) {
        coverUrl?.let { url ->
            Glide
                .with(binding.root)
                .load(url.prefixHttps())
                .placeholder(R.drawable.game_placeholder)
                .centerCrop()
                .into(binding.imageGameCover)
        }
    }
}