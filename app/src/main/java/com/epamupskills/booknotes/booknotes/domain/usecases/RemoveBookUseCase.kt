package com.epamupskills.booknotes.booknotes.domain.usecases

import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.core.UidRepository
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