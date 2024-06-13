package com.epamupskills.booknotes.booknotes.presentation.books

import com.epamupskills.booknotes.booknotes.presentation.models.BookListItem

data class BooksViewState(
    val books: List<BookListItem> = emptyList(),
)