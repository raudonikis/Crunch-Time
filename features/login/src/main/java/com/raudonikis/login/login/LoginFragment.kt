package com.raudonikis.login.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common.extensions.enableIf
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.extensions.*
import com.raudonikis.login.R
import com.raudonikis.login.databinding.FragmentLoginBinding
import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.mappers.ValidationErrorMapper
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private val binding: FragmentLoginBinding by viewBinding()

    @Inject
    lateinit var validationErrorMapper: ValidationErrorMapper

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()
        viewModel.onViewCreated()
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.loginValidationValidationState
            .onEach { onLoginValidationState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.loginEvent
            .onEach { onLoginEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.emailState
            .onEach { onEmailState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.passwordState
            .onEach { onPasswordState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.loginState
            .onEach { onLoginState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Events
     */

    private fun onLoginState(loginState: LoginState) {
        binding.progressBarLogin.showIf { loginState is LoginState.Loading }
    }

    private fun onEmailState(emailState: EmailState) {
        binding.textFieldEmail.updateError(validationErrorMapper.fromEmailState(emailState))
    }

    private fun onPasswordState(passwordState: PasswordState) {
        binding.textFieldPassword.updateError(validationErrorMapper.fromPasswordState(passwordState))

    }

    private fun onLoginValidationState(validationState: LoginValidationState) {
        binding.buttonLogin.enableIf { validationState is LoginValidationState.Enabled }
    }

    private fun onLoginEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.InitialiseFields -> {
                binding.textFieldEmail.text = event.email
                binding.checkboxRememberMe.isChecked = true
            }
            is LoginEvent.LoginSuccess -> {
                showShortSnackbar("Login success")
            }
            is LoginEvent.LoginFailure -> {
                showLongSnackbar("Login failed")
            }
        }
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        binding.apply {
            buttonSignUp.setOnClickListener {
                viewModel.onSignUpClicked()
            }
            buttonLogin.setOnClickListener {
                viewModel.onLoginClicked()
            }
            checkboxRememberMe.setOnCheckedChangeListener { _, isChecked ->
                viewModel.onRememberMeChecked(isChecked)
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