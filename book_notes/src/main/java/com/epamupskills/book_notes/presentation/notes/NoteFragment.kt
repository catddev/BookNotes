package com.epamupskills.book_notes.presentation.notes

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
import com.epamupskills.book_notes.databinding.FragmentNoteBinding
import com.epamupskills.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment : BaseFragment() {

    private val args by navArgs<NoteFragmentArgs>()
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<NoteViewModel.Factory> { factory ->
                factory.create(
                    id = if (args.noteId.isNotBlank()) args.noteId.toLong() else null,
                    bookId = args.bookId
                )
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
        //todo set toolbar with title, icon, menu
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
            if (text.isNullOrEmpty()) setText(state.note.content)
        }
    }

    private fun initViews() {
        binding.toolbar.root.apply {
            title = args.bookTitle
            setNavigationIcon(com.epamupskills.core.R.drawable.icon_back)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun setListeners() {
        binding.noteContentTextInputLayout.editText?.addTextChangedListener { editable ->
            viewModel.onIntent(EditNote(editable.toString()))
        }

        //todo add Clear button on toolbar + confirm dialog
    }
}