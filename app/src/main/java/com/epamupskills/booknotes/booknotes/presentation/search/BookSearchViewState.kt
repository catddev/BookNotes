package com.epamupskills.booknotes.booknotes.presentation.search

import com.epamupskills.booknotes.booknotes.presentation.models.BookUi

data class BookSearchViewState(
    val searchResults: List<BookUi> = emptyList(),
    val isKeyboardOpen: Boolean = true,
)