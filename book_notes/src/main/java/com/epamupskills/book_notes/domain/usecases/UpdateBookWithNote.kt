package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.core.repository.UidRepository
import javax.inject.Inject

class UpdateBookWithNote @Inject constructor(
    private val booksRepository: BooksRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(noteId: Long?, bookId: String): Result<Unit> = try {
        Result.success(
            booksRepository.updateBookWithNote(
                noteId = noteId,
                bookId = bookId,
                userId = uidRepository.getUserId()
            )
        )
    } catch (t: Throwable) {
        Result.failure(t)
    }
}