package com.raudonikis.details.game_review

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.raudonikis.common_ui.extensions.text
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.details.R
import com.raudonikis.details.databinding.DialogReviewBinding
import timber.log.Timber

class ReviewDialogFragment : DialogFragment() {

    private var _binding: DialogReviewBinding? = null
    private val binding get() = _binding!!

    private var selectedRating: GameRating = GameRating.UNDEFINED

    private var onSaveClicked: (gameRating: GameRating, comment: String) -> Unit = { _, _ -> }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogReviewBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonSave.setOnClickListener {
                if (selectedRating != GameRating.UNDEFINED) {
                    onSaveClicked.invoke(selectedRating, textInputComment.text)
                    dismiss()
                } else {
                    Toast.makeText(context, "Please select a rating", Toast.LENGTH_SHORT).show()
                }
            }
            buttonLike.setOnClickListener {
                onRatingClicked(GameRating.UP_VOTED)
            }
            buttonDislike.setOnClickListener {
                onRatingClicked(GameRating.DOWN_VOTED)
            }
        }
    }

    private fun onRatingClicked(gameRating: GameRating) {
        selectedRating = gameRating
        when (gameRating) {
            GameRating.UP_VOTED -> {
                binding.imageButtonDislike.setImageResource(R.drawable.ic_dislike)
                binding.imageButtonLike.setImageResource(R.drawable.ic_like_filled)
            }
            GameRating.DOWN_VOTED -> {
                binding.imageButtonDislike.setImageResource(R.drawable.ic_dislike_filled)
                binding.imageButtonLike.setImageResource(R.drawable.ic_like)
            }
            else -> {
                Timber.e("onRatingClicked -> This should not happen.")
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

    fun setOnSaveClicked(onClick: (gameRating: GameRating, comment: String) -> Unit) {
        this.onSaveClicked = onClick
    }

    companion object {
        private const val DIALOG_WIDTH_PERCENTAGE = .8f
        const val TAG_REVIEW = "review"
    }
}