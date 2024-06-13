package com.epamupskills.booknotes.booknotes.domain.usecases

import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.booknotes.domain.models.Book
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {

    suspend operator fun invoke(title: String): Result<List<Book>> = try {
        Result.success(booksRepository.searchBooksByTitle(title = title))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}