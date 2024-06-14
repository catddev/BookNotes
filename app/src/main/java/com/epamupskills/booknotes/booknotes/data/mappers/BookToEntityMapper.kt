package com.epamupskills.booknotes.booknotes.data.mappers

import com.epamupskills.booknotes.booknotes.data.entities.BookEntity
import com.epamupskills.booknotes.booknotes.domain.models.Book
import com.epamupskills.booknotes.core.base.BaseMapper
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