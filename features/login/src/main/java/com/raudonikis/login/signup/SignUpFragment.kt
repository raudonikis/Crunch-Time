package com.raudonikis.login.signup

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common.extensions.enableIf
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.showLongSnackbar
import com.raudonikis.common_ui.extensions.showShortSnackbar
import com.raudonikis.common_ui.extensions.updateError
import com.raudonikis.login.R
import com.raudonikis.login.databinding.FragmentSignUpBinding
import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.UsernameState
import com.raudonikis.login.validation.mappers.ValidationErrorMapper
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()
    private val binding: FragmentSignUpBinding by viewBinding()

    @Inject
    lateinit var validationErrorMapper: ValidationErrorMapper

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
        viewModel.emailState
            .onEach { onEmailState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.passwordState
            .onEach { onPasswordState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.passwordConfirmState
            .onEach { onPasswordConfirmState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.usernameState
            .onEach { onUsernameState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.signUpValidationState
            .onEach { onSignUpValidationState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.signUpEvent
            .onEach { onSignUpEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.signUpState
            .onEach { onSignUpState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Events
     */
    private fun onSignUpState(signUpState: SignUpState) {
        binding.progressBarSignUp.showIf { signUpState == SignUpState.LOADING }
    }

    private fun onUsernameState(usernameState: UsernameState) {
        binding.textFieldUsername.updateError(validationErrorMapper.fromUsernameState(usernameState))
    }

    private fun onPasswordState(passwordState: PasswordState) {
        binding.textFieldPassword.updateError(validationErrorMapper.fromPasswordState(passwordState))
    }

    private fun onPasswordConfirmState(passwordState: PasswordState) {
        binding.textFieldPasswordConfirm.updateError(
            validationErrorMapper.fromPasswordState(
                passwordState
            )
        )
    }

    private fun onEmailState(emailState: EmailState) {
        binding.textFieldEmail.updateError(validationErrorMapper.fromEmailState(emailState))
    }

    private fun onSignUpValidationState(validationState: SignUpValidationState) {
        binding.buttonSignUp.enableIf { validationState == SignUpValidationState.ENABLED }
    }

    private fun onSignUpEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.Success -> {
                showShortSnackbar("Sign up success")
            }
            is SignUpEvent.Failure -> {
                showLongSnackbar("Sign up failed")
            }
        }
    }

    /**
     * Listeners
     */
    private fun setUpInputListeners() {
        binding.apply {
            textFieldEmail.editText?.doOnTextChanged { email, _, _, _ ->
                viewModel.onEmailChanged(email.toString())
            }
            textFieldPassword.editText?.doOnTextChanged { password, _, _, _ ->
                viewModel.onPasswordChanged(password.toString())
            }
            textFieldPasswordConfirm.editText?.doOnTextChanged { passwordConfirm, _, _, _ ->
                viewModel.onPasswordConfirmChanged(passwordConfirm.toString())
            }
            textFieldUsername.editText?.doOnTextChanged { username, _, _, _ ->
                viewModel.onUsernameChanged(username.toString())
            }
        }
    }

    private fun setUpListeners() {
        binding.apply {
            buttonLogin.setOnClickListener {
                viewModel.onLoginClicked()
            }
            buttonSignUp.setOnClickListener {
                viewModel.onSignUpClicked()
            }
        }
        setUpInputListeners()
    }
}