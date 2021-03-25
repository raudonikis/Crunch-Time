package com.raudonikis.login.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common.extensions.enableIf
import com.raudonikis.common.extensions.hide
import com.raudonikis.common.extensions.show
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.showLongSnackbar
import com.raudonikis.common_ui.extensions.showShortSnackbar
import com.raudonikis.common_ui.extensions.text
import com.raudonikis.login.R
import com.raudonikis.login.databinding.FragmentLoginBinding
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
        viewModel.apply {
            loginState
                .onEach { onLoginState(it) }
                .observeInLifecycle(viewLifecycleOwner)
            loginEvent
                .onEach { onLoginEvent(it) }
                .observeInLifecycle(viewLifecycleOwner)
            emailState
                .onEach {
                    binding.textFieldEmail.error = validationErrorMapper.fromEmailState(it)
                }
                .observeInLifecycle(viewLifecycleOwner)
            passwordState
                .onEach {
                    binding.textFieldPassword.error = validationErrorMapper.fromPasswordState(it)
                }
                .observeInLifecycle(viewLifecycleOwner)
        }
    }

    private fun onLoginState(state: LoginState) {
        binding.buttonLogin.enableIf { state is LoginState.Enabled }
    }

    private fun onLoginEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.InitialiseFields -> {
                binding.textFieldEmail.text = event.email
                binding.checkboxRememberMe.isChecked = true
            }
            is LoginEvent.Loading -> {
                binding.progressBarLogin.show()
            }
            is LoginEvent.LoginSuccess -> {
                showShortSnackbar("Login success")
                binding.progressBarLogin.hide()
            }
            is LoginEvent.LoginFailure -> {
                binding.progressBarLogin.hide()
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