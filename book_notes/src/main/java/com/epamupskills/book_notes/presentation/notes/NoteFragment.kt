package com.epamupskills.book_notes.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.epamupskills.book_notes.databinding.FragmentNoteBinding
import com.epamupskills.book_notes.presentation.models.NoteUi
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
                val noteId = if (args.noteId != "null") args.noteId?.toLong() else null //todo refactor
                factory.create(noteId)
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
        //todo save button + dialog
        //todo when book is deleted -> navigate to placeholder - PAW ANIMATION lottie
        //todo when note is added/updated -> popUpTo SELF + inclusive=FALSE

        // todo "updateBookWithNote" from BookRepo - add to addNoteUseCase success (not edit, only when is created)
        //todo deleting not really means just update content to empty

        //todo when navigating up - first close note and show placeholder or close app itself?


        //todo debounce 0.5sec send to VM (maybe cancel previous job)
        //todo 2 modes - textView, Edit text (disable edit text)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { note -> renderViews(note) }
            }
        }
    }

    private fun renderViews(noteUi: NoteUi) {
        //todo
    }

    private fun initViews() {
        binding.toolbar.root.apply {
            title = args.bookTitle
            setNavigationIcon(com.epamupskills.core.R.drawable.icon_back)
            setNavigationOnClickListener { findNavController().navigateUp() } //todo check
        }
    }

    private fun setListeners() {

    }
}