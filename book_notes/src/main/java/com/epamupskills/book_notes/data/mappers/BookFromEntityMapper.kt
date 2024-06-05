package com.epamupskills.book_notes.data.mappers

import com.epamupskills.book_notes.data.entities.BookEntity
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class BookFromEntityMapper @Inject constructor() : BaseMapper<BookEntity, Book>() {

    override fun transform(input: BookEntity): Book = Book(
        id = input.bookId,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
        noteId = input.noteId,
    )
}