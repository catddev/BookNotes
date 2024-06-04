package com.epamupskills.book_notes.presentation.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.epamupskills.book_notes.R
import com.epamupskills.book_notes.databinding.FragmentBooksBinding
import com.epamupskills.book_notes.presentation.books.adapter.BooksAdapter
import com.epamupskills.core.ImageLoader
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.base.BaseFragment
import com.epamupskills.core.di.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BooksFragment : BaseFragment() {

    @Inject
    @Glide
    lateinit var imageLoader: ImageLoader

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!
    private val booksAdapter by lazy { BooksAdapter(imageLoader, ::openNote, {}) } //todo
    private val viewModel by viewModels<BooksViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBooksBinding.inflate(layoutInflater).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initBaseObservers(viewModel, binding.loader.root, binding.errorAnimatedView.root)
        initViews()
        setClickListeners()
        //todo header - span 2, in the center
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun openNote(bookId: String) {
        if(resources.configuration.smallestScreenWidthDp >= 600) { //todo const
            (childFragmentManager.findFragmentById(R.id.booksFragment) as NavHostFragment).navController.navigate(R.id.noteFragment)
        } else {
            val action = BooksFragmentDirections.actionBooksFragmentToNoteFragment(bookId)
            viewModel.onNavigationEvent(NavigateTo(action))
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { booksAdapter.submitList(it.books) }
            }
        }
    }

    private fun initViews() {
        binding.booksRecyclerView.adapter = booksAdapter
    }

    private fun setClickListeners() {

    }
}