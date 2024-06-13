package com.epamupskills.booknotes.authorization.presentation.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.epamupskills.booknotes.R
import com.epamupskills.booknotes.authorization.presentation.auth_shared.AuthViewModel
import com.epamupskills.booknotes.authorization.presentation.auth_shared.AuthViewState
import com.epamupskills.booknotes.authorization.presentation.auth_shared.ConfirmPassword
import com.epamupskills.booknotes.authorization.presentation.auth_shared.EnterEmail
import com.epamupskills.booknotes.authorization.presentation.auth_shared.EnterPassword
import com.epamupskills.booknotes.authorization.presentation.auth_shared.SignUp
import com.epamupskills.booknotes.base.BaseFragment
import com.epamupskills.booknotes.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by hiltNavGraphViewModels(R.id.auth_nav)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignUpBinding.inflate(layoutInflater).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initBaseObservers(viewModel = viewModel, loader = binding.loader.root)
        initObservers()
        setListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state -> renderViews(state) }
            }
        }
    }

    private fun setListeners() {
        binding.signUpButton.setOnClickListener {
            viewModel.onIntent(SignUp)
            hideKeyboard()
        }

        binding.userNameInputField.root.editText?.addTextChangedListener {
            viewModel.onIntent(EnterEmail(it.toString()))
        }

        binding.passwordInputField.root.editText?.addTextChangedListener {
            viewModel.onIntent(EnterPassword(it.toString()))
        }

        binding.confirmPasswordInputField.root.editText?.addTextChangedListener {
            viewModel.onIntent(ConfirmPassword(it.toString()))
        }
    }

    private fun renderViews(state: AuthViewState) {
        with(binding) {
            userNameInputField.root.apply {
                isErrorEnabled = !state.isEmailValid && state.userCredentials.email.isNotBlank()
                error = if (isErrorEnabled) getString(R.string.error_invalid_email_text) else null
            }

            passwordInputField.root.apply {
                isErrorEnabled =
                    !state.isPasswordValid && state.userCredentials.password.isNotBlank()
                error =
                    if (isErrorEnabled) getString(R.string.error_invalid_password_text) else null
            }

            confirmPasswordInputField.root.apply {
                isErrorEnabled =
                    !state.isPasswordConfirmedCorrectly && state.confirmedPassword.isNotBlank()
                error =
                    if (isErrorEnabled) getString(R.string.error_wrong_password_confirmation_text) else null
            }

            signUpButton.isEnabled = state.isSignUpButtonEnabled
        }

    }

    private fun initViews() {
        with(viewModel.state.value) {
            binding.run {
                confirmPasswordInputField.root.apply {
                    setHint(R.string.sign_up_confirm_password_hint)
                    editText?.setText(confirmedPassword)
                }
                userNameInputField.root.editText?.setText(userCredentials.email)
                passwordInputField.root.editText?.setText(userCredentials.password)
            }
        }
    }
}