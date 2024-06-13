package com.epamupskills.booknotes.booknotes.data.mappers

import com.epamupskills.booknotes.booknotes.data.models.BookDto
import com.epamupskills.booknotes.booknotes.domain.models.Book
import com.epamupskills.booknotes.core.Constants.EMPTY
import com.epamupskills.booknotes.core.Constants.SEPARATOR_COMMA
import com.epamupskills.booknotes.base.BaseMapper
import javax.inject.Inject

class BookDtoMapper @Inject constructor() : BaseMapper<BookDto, Book>() {

    override fun transform(input: BookDto): Book = Book(
        id = input.id,
        title = input.volumeInfo.title,
        authors = input.volumeInfo.authors
            .orEmpty()
            .joinToString(separator = SEPARATOR_COMMA, postfix = EMPTY) { it },
        description = input.volumeInfo.description.orEmpty(),
        thumbnailUrl = input.volumeInfo.imageLinks?.thumbnail.orEmpty(),
    )
}