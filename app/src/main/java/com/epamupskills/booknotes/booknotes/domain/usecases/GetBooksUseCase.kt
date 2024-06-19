package com.epamupskills.booknotes.booknotes.domain.usecases

import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.core.abstraction.UidRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke() = try {
        Result.success(booksRepository.getAllBooks(uidRepository.getUserId()))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}