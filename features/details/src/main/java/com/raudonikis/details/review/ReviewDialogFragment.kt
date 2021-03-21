package com.raudonikis.details.review

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.raudonikis.details.R
import com.raudonikis.details.databinding.DialogReviewBinding

class ReviewDialogFragment : DialogFragment() {

    private var _binding: DialogReviewBinding? = null
    private val binding get() = _binding!!

    private var onSaveClicked: () -> Unit = { }

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
                onSaveClicked.invoke()
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

    fun setOnSaveClicked(onClick: () -> Unit) {
        this.onSaveClicked = onClick
    }

    companion object {
        private const val DIALOG_WIDTH_PERCENTAGE = .8f
        const val TAG_REVIEW = "review"
    }
}