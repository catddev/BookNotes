package com.epamupskills.book_notes.presentation.books

import androidx.lifecycle.viewModelScope
import com.epamupskills.book_notes.NestedBookNoteNavDirections
import com.epamupskills.book_notes.R
import com.epamupskills.book_notes.domain.interactors.BooksInteractor
import com.epamupskills.book_notes.presentation.mappers.BookToUiMapper
import com.epamupskills.book_notes.presentation.models.HeaderUi
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.NavigateWithNestedNavHost
import com.epamupskills.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val interactor: BooksInteractor,
    private val mapper: BookToUiMapper,
) : BaseViewModel() {

    private val _state = MutableStateFlow(BooksViewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            //todo check isEmpty, hide header if no items
            //todo use header as a key in Map<Boolean, List<Book>> ?
            //todo add headers here or in adapter?
            interactor.getBooks()
                .onSuccess { result ->
                    result.collect { books ->
                        _state.update { it.copy(books = (listOf(HeaderUi(R.string.books_with_no_notes_header_title)) + mapper.transformAll(books))) }
                    }
                }
                .onFailure {
                    _state.update { it.copy(books = emptyList()) }
                }
                .renderBaseStateByResult()
        }
    }

    fun onIntent(intent: BookUserIntent) {
        when (intent) {
            is RemoveBook -> onRemoveBook(intent.bookId)
            is OpenBookNote -> onOpenNote(intent.bookId, intent.isTablet)
        }
    }

    private fun onRemoveBook(bookId: String) {
        viewModelScope.launch { interactor.removeBook(bookId) }
    }

    private fun onOpenNote(bookId: String, isTablet: Boolean) {
        when (isTablet) {
            true -> onNavigationEvent(
                NavigateWithNestedNavHost(
                    R.id.detail_fragment_container,
                    NestedBookNoteNavDirections.toNestedNoteFragment(bookId)
                )
            )

            false -> onNavigationEvent(
                NavigateTo(BooksFragmentDirections.actionBooksFragmentToNoteFragment(bookId))
            )
        }
    }
}