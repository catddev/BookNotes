package com.epamupskills.book_notes.presentation.books

import androidx.lifecycle.viewModelScope
import com.epamupskills.book_notes.NestedBookNoteNavDirections
import com.epamupskills.book_notes.R
import com.epamupskills.book_notes.domain.interactors.BooksInteractor
import com.epamupskills.book_notes.presentation.mappers.BookListItemsMapper
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.NavigateWithConfig
import com.epamupskills.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val interactor: BooksInteractor,
    private val mapper: BookListItemsMapper,
) : BaseViewModel() {

    private val _state = MutableStateFlow(BooksViewState())
    val state = _state.asStateFlow()

    init {
        onBooksChanged()
    }

    fun onIntent(intent: BookUserIntent) {
        when (intent) {
            is RemoveBook -> onRemoveBook(intent.bookId)
            is OpenBookNote -> onOpenNote(intent.bookTitle, intent.bookId)
        }
    }

    private fun onRemoveBook(bookId: String) =
        viewModelScope.launch { interactor.removeBook(bookId).renderBaseStateByResult() }

    private fun onOpenNote(bookTitle: String, bookId: String) {
        onNavigationEvent(
            NavigateWithConfig(
                configBoolRes = com.epamupskills.core.R.bool.isTablet,
                onConfigTrueNavEvent = NavigateTo(
                    navHostId = R.id.detail_fragment_container,
                    usesChildNavController = true,
                    direction = NestedBookNoteNavDirections.toNestedNoteFragment(
                        bookTitle = bookTitle,
                        bookId = bookId,
                    ),
                ),
                onConfigFalseNavEvent = NavigateTo(
                    direction = BooksFragmentDirections.actionBooksFragmentToNoteFragment(
                        bookTitle = bookTitle,
                        bookId = bookId,
                    )
                )
            )
        )
    }

    private fun onBooksChanged() {
        viewModelScope.launch {
            interactor.getBooks()
                .onSuccess { result ->
                    result
                        .distinctUntilChanged()
                        .collect { books ->
                        _state.update { it.copy(books = mapper.mapWithHeadersByNotes(books)) }
                    }
                }
                .onFailure {
                    _state.update { it.copy(books = emptyList()) }
                }.renderBaseStateByResult()
        }
    }
}