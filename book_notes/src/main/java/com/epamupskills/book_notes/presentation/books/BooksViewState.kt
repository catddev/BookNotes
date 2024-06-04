package com.epamupskills.book_notes.presentation.books

import com.epamupskills.book_notes.presentation.models.BookUi

data class BooksViewState(
    val books: List<BookUi> = emptyList(),
)
