package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.core.repository.UidRepository
import javax.inject.Inject

class RemoveBookUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(bookId: String) = try {
        Result.success(
            booksRepository.removeBook(
                bookId = bookId,
                userId = uidRepository.getUserId()
            )
        )
    } catch (t: Throwable) {
        Result.failure(t)
    }
}