package com.epamupskills.book_notes.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.epamupskills.book_notes.databinding.FragmentBookSearchBinding
import com.epamupskills.book_notes.presentation.search.adapter.BookSearchResultsAdapter
import com.epamupskills.book_notes.presentation.utils.BookDiffUtils
import com.epamupskills.core.ImageLoader
import com.epamupskills.core.base.BaseFragment
import com.epamupskills.core.di.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookSearchFragment : BaseFragment() {

    @Inject
    @Glide
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var diffUtils: BookDiffUtils

    private var _binding: FragmentBookSearchBinding? = null
    private val binding get() = _binding!!
    private val booksAdapter by lazy {
        BookSearchResultsAdapter(
            imageLoader = imageLoader,
            onClickListener = ::onToggleBook,
            diffUtils = diffUtils,
        )
    }
    private val viewModel by viewModels<BookSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBookSearchBinding.inflate(layoutInflater).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initBaseObservers(
            baseViewModel = viewModel,
            loader = binding.loader.root,
            errorView = binding.errorAnimatedView.root
        )
        initViews()
        setClickListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onToggleBook(id: String) {
        viewModel.onIntent(ToggleBook(id))
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { booksAdapter.setBooks(it.searchResults) }
            }
        }
    }

    private fun initViews() {
        binding.searchResultRecyclerView.adapter = booksAdapter
    }

    private fun setClickListeners() {
        //todo enter + search + hideKeyboard
        //todo selector background color
        binding.searchButton.setOnClickListener {
            val input = binding.searchEditText.root.editText?.text.toString()
            viewModel.onIntent(Search(input))
            hideKeyboard()
        }
    }
}