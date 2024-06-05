package com.epamupskills.book_notes.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.epamupskills.book_notes.databinding.FragmentNoteBinding
import com.epamupskills.core.base.BaseFragment

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>()

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
        //todo set toolbar with title, icon, menu
        //todo save button + dialog
        //todo when deleted -> navigate to placeholder - PAW ANIMATION lottie
        //todo when added/updated -> popUpTo SELF + inclusive=FALSE

        // todo "updateBookWithNote" from BookRepo - add to addNoteUseCase success (not edit, only when is created)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}