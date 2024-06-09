package com.epamupskills.book_notes.presentation.search

import com.epamupskills.book_notes.presentation.models.BookUi

data class BookSearchViewState(
    val searchResults: List<BookUi> = emptyList(),
    val isKeyboardOpen: Boolean = true,
)