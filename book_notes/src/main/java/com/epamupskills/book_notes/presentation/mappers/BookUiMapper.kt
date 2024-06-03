package com.epamupskills.book_notes.presentation.mappers

import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class BookUiMapper @Inject constructor() : BaseMapper<Book, BookUi>() {

    override fun mapFrom(input: Book): BookUi = BookUi(
        id = input.id,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
    )

    override fun mapTo(input: BookUi): Book = Book(
        id = input.id,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
    )
}