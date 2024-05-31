package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BookSearchRepository
import com.epamupskills.book_notes.domain.models.Book
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val repository: BookSearchRepository,
) {

    suspend operator fun invoke(title: String): Result<List<Book>> = try {
        Result.success(repository.getBooksByTitle(title))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}