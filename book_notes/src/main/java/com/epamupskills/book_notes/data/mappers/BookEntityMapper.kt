package com.epamupskills.book_notes.data.mappers

import com.epamupskills.book_notes.data.entities.BookEntity
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class BookEntityMapper @Inject constructor() : BaseMapper<BookEntity, Book>() {

    override fun mapFrom(input: BookEntity): Book = Book(
        id = input.bookId,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
    )

    override fun mapTo(input: Book): BookEntity = BookEntity(
        bookId = input.id,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
    )
}