package com.epamupskills.book_notes.presentation.books

import androidx.lifecycle.viewModelScope
import com.epamupskills.book_notes.domain.interactors.BooksInteractor
import com.epamupskills.book_notes.presentation.mappers.BookUiMapper
import com.epamupskills.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val booksInteractor: BooksInteractor,
    private val mapper: BookUiMapper,
) : BaseViewModel() {

    private val _state = MutableStateFlow(BooksViewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            booksInteractor.getBooks()
                .onSuccess { result ->
                    result.collect { books ->
                        _state.update { it.copy(books = mapper.mapAll(books)) }
                    }
                }
                .onFailure {
                    _state.update { it.copy(books = emptyList()) }
                }
                .renderBaseStateByResult()
        }
    }
}