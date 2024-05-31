package com.epamupskills.book_notes.data.mappers

import com.epamupskills.book_notes.data.models.BookDto
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class BookDtoMapper @Inject constructor() : BaseMapper<BookDto, Book>() {

    override fun transform(input: BookDto): Book = Book(
        id = input.id,
        title = input.volumeInfo.title,
        authors = input.volumeInfo.authors.orEmpty(),
        description = input.volumeInfo.description.orEmpty(),
        thumbnailUrl = input.volumeInfo.imageLinks?.thumbnail.orEmpty(),
    )
}