package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.profile.databinding.FragmentProfileBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        binding.apply {
            cardPlayed.setOnClickListener {

            }
            cardPlaying.setOnClickListener {

            }
            cardWant.setOnClickListener {

            }
        }
    }
}