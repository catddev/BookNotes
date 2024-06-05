package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.core.repository.UidRepository
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