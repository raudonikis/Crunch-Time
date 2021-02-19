package com.raudonikis.login.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.login.R
import com.raudonikis.login.databinding.FragmentSignUpBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()
    private val binding: FragmentSignUpBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            buttonLogin.setOnClickListener {
                viewModel.navigateToLogin()
            }
            buttonSignUp.setOnClickListener {
                //todo
            }
        }
    }
}