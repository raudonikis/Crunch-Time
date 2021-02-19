package com.raudonikis.login.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.login.R
import com.raudonikis.login.databinding.FragmentLoginBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private val binding: FragmentLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            buttonSignUp.setOnClickListener {
                viewModel.navigateToSignUp()
            }
            buttonLogin.setOnClickListener {
                //todo
                viewModel.navigateToHome()
            }
        }
    }
}