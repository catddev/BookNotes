package com.epamupskills.book_notes.presentation.books

import com.epamupskills.book_notes.presentation.models.BookListItem

data class BooksViewState(
    val books: List<BookListItem> = emptyList(),
)
