package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.core.repository.UidRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckBookIsSavedUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(bookId: String): Result<Flow<Boolean>> = try {
        Result.success(
            booksRepository.isBookSaved(
                bookId = bookId,
                userId = uidRepository.getUserId()
            )
        )
    } catch (t: Throwable) {
        Result.failure(t)
    }
}