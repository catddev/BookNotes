package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BooksRepository,
) {

    suspend operator fun invoke() = try {
        Result.success(repository.getAllBooks())
    } catch (t: Throwable) {
        Result.failure(t)
    }
}