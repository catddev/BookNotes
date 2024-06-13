package com.epamupskills.booknotes.booknotes.domain.usecases

import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.core.UidRepository
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