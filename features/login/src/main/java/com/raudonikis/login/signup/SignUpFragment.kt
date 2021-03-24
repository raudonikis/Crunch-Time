package com.raudonikis.login.signup

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.login.R
import com.raudonikis.login.databinding.FragmentSignUpBinding
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
        setUpInputListeners()
        setUpListeners()
        setUpObservers()
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.apply {
            emailState
                .onEach {
                    binding.textFieldEmail.error =
                        validationErrorMapper.fromEmailState(it)
                }
                .observeInLifecycle(viewLifecycleOwner)
            passwordState
                .onEach {
                    binding.textFieldPassword.error =
                        validationErrorMapper.fromPasswordState(it)
                }
                .observeInLifecycle(viewLifecycleOwner)
            passwordConfirmState
                .onEach {
                    binding.textFieldPasswordConfirm.error =
                        validationErrorMapper.fromPasswordState(it)
                }
                .observeInLifecycle(viewLifecycleOwner)
            usernameState
                .onEach {
                    binding.textFieldUsername.error =
                        validationErrorMapper.fromUsernameState(it)
                }
                .observeInLifecycle(viewLifecycleOwner)
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
                viewModel.navigateToLogin()
            }
            buttonSignUp.setOnClickListener {
                //todo
            }
        }
    }
}