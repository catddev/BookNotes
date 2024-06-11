package com.epamupskills.book_notes.presentation.mappers

import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class BookFromUiMapper @Inject constructor() : BaseMapper<BookUi, Book>() {

    override fun transform(input: BookUi): Book = Book(
        id = input.id,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
        note = input.note,
    )
}