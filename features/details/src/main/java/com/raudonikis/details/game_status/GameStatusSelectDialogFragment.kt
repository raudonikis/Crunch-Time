package com.raudonikis.details.game_status

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.raudonikis.common_ui.extensions.check
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.details.R
import com.raudonikis.details.databinding.DialogGameStatusSelectBinding

class GameStatusSelectDialogFragment : DialogFragment() {

    private var _binding: DialogGameStatusSelectBinding? = null
    private val binding get() = _binding!!

    private var gameStatus: GameStatus = GameStatus.EMPTY
    private var onUpdateClicked: (gameStatus: GameStatus) -> Unit = { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogGameStatusSelectBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (gameStatus) {
            GameStatus.PLAYED -> binding.radioButtonPlayed.check()
            GameStatus.PLAYING -> binding.radioButtonPlaying.check()
            GameStatus.WANT -> binding.radioButtonWant.check()
        }
        binding.apply {
            buttonUpdate.setOnClickListener {
                val gameStatus = when (radioGroupGameStatus.checkedRadioButtonId) {
                    radioButtonPlayed.id -> GameStatus.PLAYED
                    radioButtonPlaying.id -> GameStatus.PLAYING
                    radioButtonWant.id -> GameStatus.WANT
                    else -> GameStatus.EMPTY

                }
                onUpdateClicked.invoke(gameStatus)
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog)
    }

    override fun onStart() {
        super.onStart()
        val screenWidth = (resources.displayMetrics.widthPixels * DIALOG_WIDTH_PERCENTAGE).toInt()
        dialog?.window?.apply {
            setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setGameStatus(gameStatus: GameStatus) {
        this.gameStatus = gameStatus
    }

    fun setOnUpdateClicked(onClick: (gameStatus: GameStatus) -> Unit) {
        this.onUpdateClicked = onClick
    }

    companion object {
        private const val DIALOG_WIDTH_PERCENTAGE = .8f
        const val TAG_GAME_STATUS_UPDATE = "game_status_update"
    }
}