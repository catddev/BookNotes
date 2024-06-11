package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import javax.inject.Inject

class CheckBookExistsUseCase @Inject constructor(
    private val repository: BooksRepository,
) {

    suspend operator fun invoke(noteId: Long): Result<Boolean> = try {
        Result.success(repository.doesBookExistByNote(noteId))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}