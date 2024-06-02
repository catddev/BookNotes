package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import javax.inject.Inject

class RemoveBookUseCase @Inject constructor(
    private val repository: BooksRepository,
) {
}