package com.raudonikis.common_ui.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.LayoutGameStatusBinding
import com.raudonikis.data_domain.game_status.GameStatus

class GameStatusLayout(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val binding: LayoutGameStatusBinding

    init {
        val view = inflate(context, R.layout.layout_game_status, this)
        binding = LayoutGameStatusBinding.bind(view)
    }

    fun setGameStatus(gameStatus: GameStatus) {
        binding.apply {
            layoutRoot.show()
            val backgroundColor = when (gameStatus) {
                GameStatus.PLAYED -> ContextCompat.getColor(context, R.color.game_status_played)
                GameStatus.PLAYING -> ContextCompat.getColor(context, R.color.game_status_playing)
                GameStatus.WANT -> ContextCompat.getColor(context, R.color.game_status_want)
                else -> {
                    layoutRoot.hide()
                    null
                }
            }
            backgroundColor?.let { color ->
                cardGameStatus.setCardBackgroundColor(color)
            }
            textGameStatus.text = gameStatus.toString().capitalize()
        }
    }
}