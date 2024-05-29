package com.epamupskills.authorization.presentation.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.epamupskills.authorization.R
import com.epamupskills.authorization.databinding.FragmentSignInBinding
import com.epamupskills.authorization.presentation.auth_shared.AuthViewModel
import com.epamupskills.authorization.presentation.auth_shared.AuthViewState
import com.epamupskills.authorization.presentation.auth_shared.EnterEmail
import com.epamupskills.authorization.presentation.auth_shared.EnterPassword
import com.epamupskills.authorization.presentation.auth_shared.SignIn
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.base.BaseFragment
import kotlinx.coroutines.launch

class SignInFragment : BaseFragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by hiltNavGraphViewModels(R.id.auth_nav)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignInBinding.inflate(layoutInflater).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initBaseObservers(baseViewModel = viewModel, loader = binding.loader.root)
        initObservers()
        setListeners()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state -> renderViews(state) }
            }
        }
    }

    private fun setListeners() {
        binding.signInButton.setOnClickListener {
            viewModel.onIntent(SignIn)
            hideKeyboard()
        }

        binding.signUpButton.setOnClickListener {
            viewModel.onNavigationEvent(NavigateTo(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()))
            hideKeyboard()
        }

        binding.userNameInputField.root.editText?.addTextChangedListener {
            viewModel.onIntent(EnterEmail(it.toString()))
        }

        binding.passwordInputField.root.editText?.addTextChangedListener {
            viewModel.onIntent(EnterPassword(it.toString()))
        }
    }

    private fun renderViews(state: AuthViewState) {
        binding.signInButton.isEnabled = state.isSignInButtonEnabled
    }

    private fun initViews() {
        with(viewModel.state.value) {
            binding.userNameInputField.root.editText?.setText(userCredentials.email)
            binding.passwordInputField.root.editText?.setText(userCredentials.password)
        }
    }
}