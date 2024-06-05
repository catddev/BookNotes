package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.repository.UidRepository
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(title: String): Result<List<Book>> = try {
        Result.success(
            booksRepository.searchBooksByTitle(
                title = title,
                userId = uidRepository.getUserId()
            )
        )
    } catch (t: Throwable) {
        Result.failure(t)
    }
}