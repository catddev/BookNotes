package com.epamupskills.book_notes.data.mappers

import com.epamupskills.book_notes.data.entities.BookEntity
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class BookToEntityMapper @Inject constructor() : BaseMapper<Book, BookEntity>() {

    override fun transform(input: Book): BookEntity = BookEntity(
        bookId = input.id,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
        note = input.note,
    )
}