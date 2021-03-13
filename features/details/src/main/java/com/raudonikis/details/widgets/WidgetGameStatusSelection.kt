package com.raudonikis.details.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.details.R
import com.raudonikis.details.databinding.WidgetGameStatusSelectionBinding

class WidgetGameStatusSelection(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var currentStatus: GameStatus = GameStatus.EMPTY
    private val binding: WidgetGameStatusSelectionBinding

    init {
        val view = inflate(context, R.layout.widget_game_status_selection, this)
        binding = WidgetGameStatusSelectionBinding.bind(view)
    }

    fun onStatusSelected(f: (status: GameStatus) -> Unit) {
        binding.statusWant.setOnClickListener {
            f(GameStatus.WANT)
        }
        binding.statusPlayed.setOnClickListener {
            f(GameStatus.PLAYED)
        }
        binding.statusPlaying.setOnClickListener {
            f(GameStatus.PLAYING)
        }
    }

    fun setGameStatus(status: GameStatus) {
        currentStatus = status
        binding.statusPlaying.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.statusPlayed.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.statusWant.setTextColor(resources.getColor(R.color.colorPrimary))
        when (currentStatus) {
            GameStatus.PLAYING -> {
                binding.statusPlaying.setTextColor(resources.getColor(R.color.colorAccent))
            }
            GameStatus.PLAYED -> {
                binding.statusPlayed.setTextColor(resources.getColor(R.color.colorAccent))
            }
            GameStatus.WANT -> {
                binding.statusWant.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }
    }
}