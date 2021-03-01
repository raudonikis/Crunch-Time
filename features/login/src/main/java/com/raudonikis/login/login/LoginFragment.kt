package com.raudonikis.login.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.login.R
import com.raudonikis.login.databinding.FragmentLoginBinding
import com.raudonikis.login.validation.EmailValidationResult
import com.raudonikis.login.validation.PasswordValidationResult
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private val binding: FragmentLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()
        setUpInputValidations()
    }

    private fun setUpInputValidations() {
        binding.apply {
            textFieldEmail.editText?.doOnTextChanged { email, _, _, _ ->
                viewModel.onEmailChanged(email.toString())
            }
            textFieldPassword.editText?.doOnTextChanged { password, _, _, _ ->
                viewModel.onPasswordChanged(password.toString())
            }
        }
    }

    private fun setUpObservers() {
        viewModel.emailStateObservable().observe(viewLifecycleOwner) { emailState ->
            when (emailState) {
                EmailValidationResult.EMAIL_INITIAL,
                EmailValidationResult.EMAIL_VALID -> {
                    binding.textFieldEmail.error = ""
                }
                else -> {
                    binding.textFieldEmail.error = emailState.name
                }
            }
        }
        viewModel.passwordStateObservable().observe(viewLifecycleOwner) { passwordState ->
            when (passwordState) {
                PasswordValidationResult.PASSWORD_INITIAL,
                PasswordValidationResult.PASSWORD_VALID -> {
                    binding.textFieldPassword.error = ""
                }
                else -> {
                    binding.textFieldPassword.error = passwordState.name
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.apply {
            buttonSignUp.setOnClickListener {
                viewModel.navigateToSignUp()
            }
            buttonLogin.setOnClickListener {
                //todo
                viewModel.login(
                    textFieldEmail.editText?.text?.toString(),
                    textFieldPassword.editText?.text?.toString()
                )
            }
        }
    }
}