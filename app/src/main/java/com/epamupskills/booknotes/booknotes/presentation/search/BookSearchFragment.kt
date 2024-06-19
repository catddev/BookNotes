package com.epamupskills.booknotes.booknotes.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.epamupskills.booknotes.booknotes.presentation.models.BookUi
import com.epamupskills.booknotes.booknotes.presentation.search.adapter.BookSearchResultsAdapter
import com.epamupskills.booknotes.core.abstraction.ImageLoader
import com.epamupskills.booknotes.core.base.BaseFragment
import com.epamupskills.booknotes.databinding.FragmentBookSearchBinding
import com.epamupskills.booknotes.di.Glide
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
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onIntent(Search(newText.orEmpty()))
                    return true
                }
            })
        }
    }

    private fun initViews() {
        binding.searchResultRecyclerView.adapter = booksAdapter
    }
}