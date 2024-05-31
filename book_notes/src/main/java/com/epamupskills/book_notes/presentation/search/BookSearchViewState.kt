package com.epamupskills.book_notes.presentation.search

import com.epamupskills.book_notes.presentation.models.BookUi

data class BookSearchViewState(
    val searchInput: String = "",
    val searchResults: List<BookUi> = emptyList(),
) {
    val resultMap: Map<String, BookUi> = searchResults.associateBy { book -> book.id }
}