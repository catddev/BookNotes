package com.epamupskills.book_notes.presentation.search

import androidx.lifecycle.viewModelScope
import com.epamupskills.book_notes.domain.interactors.BookSearchInteractor
import com.epamupskills.book_notes.presentation.mappers.BookFromUiMapper
import com.epamupskills.book_notes.presentation.mappers.BookToUiMapper
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
    private val bookToUiMapper: BookToUiMapper,
    private val bookFromUiMapper: BookFromUiMapper,
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
        viewModelScope.launch {
            input.takeIf { it.isNotBlank() }?.let {
                loading.value = true
                interactor.searchBooks(input.trim())
                    .onSuccess { books ->
                        _state.update {
                            it.copy(searchResults = bookToUiMapper.transformAll(books))
                        }
                    }
                    .onFailure { _state.update { it.copy(searchResults = emptyList()) } }
                    .renderBaseStateByResult()
            }
        }
    }

    private fun onToggleBookmark(id: String) {
        launchCatching {
            val results = _state.value.resultMap as MutableMap
            val bookUi = requireNotNull(results[id])
            results[id] = bookUi.copy(isBookmarked = !bookUi.isBookmarked).also { book ->
                launch {
                    when {
                        book.isBookmarked -> interactor.saveBook(bookFromUiMapper.transform(book))
                        else -> interactor.removeBook(book.id)
                    }
                }
            }

            _state.update {
                it.copy(searchResults = results.values.toList())
            }
        }
    }
}