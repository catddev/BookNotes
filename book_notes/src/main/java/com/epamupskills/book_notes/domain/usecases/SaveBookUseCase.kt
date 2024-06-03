package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.book_notes.domain.models.Book
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val repository: BooksRepository,
) {

    suspend operator fun invoke(book: Book) = try {
        Result.success(repository.addBook(book))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}