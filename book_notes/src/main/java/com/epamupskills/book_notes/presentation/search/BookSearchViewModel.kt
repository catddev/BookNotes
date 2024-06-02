package com.epamupskills.book_notes.presentation.search

import androidx.lifecycle.viewModelScope
import com.epamupskills.book_notes.domain.interactors.BookSearchInteractor
import com.epamupskills.book_notes.presentation.mappers.BookUiMapper
import com.epamupskills.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val interactor: BookSearchInteractor,
    private val mapper: BookUiMapper
) : BaseViewModel() {

    private val _state = MutableStateFlow(BookSearchViewState())
    val state = _state.asStateFlow()

    fun onIntent(intent: SearchBookUserIntent) {
        when (intent) {
            is Search -> onSearchBook(intent.input)
            is ToggleBookmark -> onToggleBookmark(intent.bookId)
        }
    }

    private fun onSearchBook(input: String) {
        loading.value = true
        viewModelScope.launch {
            interactor.searchBooks(input.trim())
                .onSuccess { books ->
                    _state.update {
                        it.copy(searchResults = mapper.transformAll(books))
                    }
                }
                .onFailure { _state.update { it.copy(searchResults = emptyList()) } }
                .renderBaseStateByResult()
        }
    }

    private fun onToggleBookmark(id: String) {
        launchCatching {
            val results = _state.value.resultMap as MutableMap

            results.let {
                val book = requireNotNull(results[id])
                results[id] = book.copy(isBookmarked = !book.isBookmarked)
            }

            _state.update {
                it.copy(searchResults = results.values.toList())
            }

            //todo change status in Room! async
            // todo async mapper bookmarks in Repo, not here
        }
    }
}