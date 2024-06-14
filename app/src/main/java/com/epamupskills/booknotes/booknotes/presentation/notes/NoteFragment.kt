package com.epamupskills.booknotes.booknotes.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.epamupskills.booknotes.R
import com.epamupskills.booknotes.core.base.BaseFragment
import com.epamupskills.booknotes.databinding.FragmentNoteBinding
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch

class NoteFragment : BaseFragment() {

    private val args by navArgs<NoteFragmentArgs>()
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<NoteViewModel.Factory> { factory ->
                factory.create(bookId = args.bookId)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNoteBinding.inflate(layoutInflater).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseObservers(viewModel)
        initObservers()
        initViews()
        setListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    renderViews(state)
                }
            }
        }
    }

    private fun renderViews(state: NoteViewState) {
        binding.noteContentTextInputLayout.editText?.apply {
            if (text.isNullOrEmpty() || state.note.isEmpty()) setText(state.note)
        }
    }

    private fun initViews() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.icon_back)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
        binding.toolbarTitle.text = args.bookTitle
    }

    private fun setListeners() {
        binding.noteContentTextInputLayout.editText?.addTextChangedListener { editable ->
            viewModel.onIntent(EditNote(editable.toString()))
        }
        binding.clearNoteButton.setOnClickListener {
            viewModel.onIntent(ClearNote)
        }
        binding.root.setOnClickListener {
            binding.noteContentEditText.requestFocus()
        }
    }
}