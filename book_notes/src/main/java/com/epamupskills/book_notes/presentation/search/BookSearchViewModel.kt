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
            is ToggleBook -> onToggleBook(intent.bookId)
        }
    }

    private fun onSearchBook(input: String) {
        onLoading()
        viewModelScope.launch {
            interactor.searchBooks(input.trim())
                .onSuccess { books ->
                    _state.update {
                        it.copy(searchResults = mapper.transformAll(books))
                    }
                }.onResult()
        }
    }

    private fun onToggleBook(id: String) {
        launchCatching {
            val book = _state.value.resultMap[id]?.run {
                copy(isAddedToCollection = !isAddedToCollection)
            }
            val updatedEntries = _state.value.resultMap.toMutableMap().also {
                it[id] = requireNotNull(book)
            }
            _state.update {
                it.copy(searchResults = updatedEntries.values.toList())
            }

            //todo change status in Room!
        }
    }
}