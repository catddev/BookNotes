package com.epamupskills.book_notes.presentation.mappers

import com.epamupskills.book_notes.R
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.book_notes.presentation.models.BookListItem
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.book_notes.presentation.models.HeaderUi
import javax.inject.Inject

class BookListItemsMapper @Inject constructor(
    private val mapper: BookToUiMapper,
) {

    fun mapWithHeadersByNotes(books: List<Book>): List<BookListItem> =
        mutableListOf<BookListItem>().apply {
            mapper.transformAll(books).partition { book -> book.hasNote }.run {
                first.letNotEmpty { booksWithNotes ->
                    add(HeaderUi(R.string.books_with_notes_header_title))
                    addAll(booksWithNotes)
                }

                second.letNotEmpty { booksWithoutNotes ->
                    add(HeaderUi(R.string.books_with_no_notes_header_title))
                    addAll(booksWithoutNotes)
                }
            }
        }

    private fun List<BookUi>.letNotEmpty(block: (List<BookUi>) -> Unit) =
        if (isNotEmpty()) block.invoke(this) else Unit
}