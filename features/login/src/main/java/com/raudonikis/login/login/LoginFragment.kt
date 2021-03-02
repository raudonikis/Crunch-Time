package com.raudonikis.login.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.extensions.clearError
import com.raudonikis.common_ui.extensions.showLongSnackbar
import com.raudonikis.common_ui.extensions.text
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

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.loginEventObservable().observe(viewLifecycleOwner) { loginState ->
            when (loginState) {
                is LoginEvent.Loading -> {
                    binding.progressBarLogin.show()
                }
                is LoginEvent.LoginSuccess -> {
                    binding.progressBarLogin.hide()
                }
                is LoginEvent.LoginFailure -> {
                    binding.progressBarLogin.hide()
                    //todo improve error message
                    showLongSnackbar("Login failed")
                }
                is LoginEvent.InvalidInputs -> {
                    binding.progressBarLogin.hide()
                    //todo improve error message
                    showLongSnackbar("Invalid inputs")
                }
            }
        }
        viewModel.emailStateObservable().observe(viewLifecycleOwner) { emailState ->
            when (emailState) {
                EmailValidationResult.EMAIL_INITIAL,
                EmailValidationResult.EMAIL_VALID -> {
                    binding.textFieldEmail.clearError()
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
                    binding.textFieldPassword.clearError()
                }
                else -> {
                    binding.textFieldPassword.error = passwordState.name
                }
            }
        }
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        binding.apply {
            buttonSignUp.setOnClickListener {
                viewModel.navigateToSignUp()
            }
            buttonLogin.setOnClickListener {
                viewModel.login(
                    textFieldEmail.text,
                    textFieldPassword.text
                )
            }
        }
        setUpInputListeners()
    }

    private fun setUpInputListeners() {
        binding.apply {
            textFieldEmail.editText?.doOnTextChanged { email, _, _, _ ->
                viewModel.onEmailChanged(email.toString())
            }
            textFieldPassword.editText?.doOnTextChanged { password, _, _, _ ->
                viewModel.onPasswordChanged(password.toString())
            }
        }
    }
}