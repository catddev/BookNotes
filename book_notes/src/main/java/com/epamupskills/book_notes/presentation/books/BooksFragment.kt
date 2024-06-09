package com.epamupskills.book_notes.presentation.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.epamupskills.book_notes.databinding.FragmentBooksBinding
import com.epamupskills.book_notes.presentation.books.adapter.BooksAdapter
import com.epamupskills.core.ImageLoader
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
    private val booksAdapter by lazy { BooksAdapter(imageLoader, ::openNote, ::removeBook) }
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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun removeBook(bookId: String) {
        viewModel.onIntent(RemoveBook(bookId))
    }

    private fun openNote(noteId: Long?, bookTitle: String) {
        //todo managed popUp - show default placeholder
        //todo when book is deleted -> subcribe to note and when note is deleted (id = null???)- popBackStack to default placeholder?
        viewModel.onIntent(
            OpenBookNote(
                noteId = noteId,
                bookTitle = bookTitle,
                isTablet = resources.getBoolean(com.epamupskills.core.R.bool.isTablet)
            )
        )
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { booksAdapter.submitList(it.books) }
            }
        }
    }

    private fun initViews() {
        binding.booksRecyclerView.apply {
            adapter = booksAdapter
            if (!resources.getBoolean(com.epamupskills.core.R.bool.isTablet) &&
                resources.getBoolean(com.epamupskills.core.R.bool.isLand)
            ) {
                layoutManager = StaggeredGridLayoutManager(
                    LIST_ITEMS_SPAN_COUNT,
                    StaggeredGridLayoutManager.VERTICAL
                ).apply {
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                }
            }
        }
    }

    companion object {
        private const val LIST_ITEMS_SPAN_COUNT = 2
    }
}