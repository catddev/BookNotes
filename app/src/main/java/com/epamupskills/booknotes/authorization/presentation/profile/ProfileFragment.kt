package com.epamupskills.booknotes.authorization.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.epamupskills.booknotes.authorization.presentation.auth_shared.Confirm
import com.epamupskills.booknotes.authorization.presentation.auth_shared.DeleteAccount
import com.epamupskills.booknotes.authorization.presentation.auth_shared.SignOut
import com.epamupskills.booknotes.base.BaseFragment
import com.epamupskills.booknotes.databinding.FragmentProfileBinding
import com.epamupskills.booknotes.ui.ConfirmDialog.Companion.RESULT_KEY

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProfileBinding.inflate(layoutInflater).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseObservers(viewModel = viewModel, loader = binding.loader.root)
        initViews()
        setListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setListeners() {
        binding.deleteAccountButton.setOnClickListener {
            onUserActionConfirmed { viewModel.onIntent(DeleteAccount) }
        }

        binding.signOutButton.setOnClickListener {
            onUserActionConfirmed { viewModel.onIntent(SignOut) }
        }
    }

    private fun onUserActionConfirmed(block: () -> Unit) {
        viewModel.onIntent(Confirm)
        setFragmentResultListener(RESULT_KEY) { key, bundle ->
            if (bundle.getBoolean(key)) block.invoke()
        }
    }

    private fun initViews() {
        binding.userEmailTextView.text = viewModel.state.value.email
    }
}