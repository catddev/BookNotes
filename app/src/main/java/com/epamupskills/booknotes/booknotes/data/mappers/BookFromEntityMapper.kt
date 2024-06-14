package com.epamupskills.booknotes.booknotes.data.mappers

import com.epamupskills.booknotes.booknotes.data.entities.BookEntity
import com.epamupskills.booknotes.booknotes.domain.models.Book
import com.epamupskills.booknotes.core.base.BaseMapper
import javax.inject.Inject

class BookFromEntityMapper @Inject constructor() : BaseMapper<BookEntity, Book>() {

    override fun transform(input: BookEntity): Book = Book(
        id = input.bookId,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
        note = input.note,
    )
}