package com.epamupskills.book_notes.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.epamupskills.book_notes.databinding.FragmentBookSearchBinding
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.book_notes.presentation.search.adapter.BookSearchResultsAdapter
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

    private var _binding: FragmentBookSearchBinding? = null
    private val binding get() = _binding!!
    private val booksAdapter by lazy {
        BookSearchResultsAdapter(
            imageLoader = imageLoader,
            onClickListener = ::onToggleBookmark,
        )
    }
    private val viewModel by activityViewModels<BookSearchViewModel>()

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
            viewModel = viewModel,
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

    private fun onToggleBookmark(book: BookUi) {
        viewModel.onIntent(ToggleBookmark(book))
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect(::renderViews)
            }
        }
    }

    private fun renderViews(state: BookSearchViewState) {
        if (!state.isKeyboardOpen) hideKeyboard()
        booksAdapter.submitList(state.searchResults)
    }

    private fun setClickListeners() {
        binding.searchEditText.root.editText?.addTextChangedListener {
            viewModel.onIntent(Search(it.toString()))
        }
    }

    private fun initViews() {
        binding.searchResultRecyclerView.adapter = booksAdapter
    }
}