package com.epamupskills.booknotes.booknotes.presentation.mappers

import com.epamupskills.booknotes.booknotes.domain.models.Book
import com.epamupskills.booknotes.booknotes.presentation.models.BookUi
import com.epamupskills.booknotes.base.BaseMapper
import javax.inject.Inject

class BookToUiMapper @Inject constructor() : BaseMapper<Book, BookUi>() {

    override fun transform(input: Book): BookUi = BookUi(
        id = input.id,
        title = input.title,
        authors = input.authors,
        description = input.description,
        thumbnailUrl = input.thumbnailUrl,
        isBookmarked = input.isBookmarked,
        note = input.note,
    )
}