package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.NoteRepository
import com.epamupskills.core.repository.UidRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(bookId: String): Result<String> = try {
        Result.success(noteRepository.getNote(bookId = bookId, userId = uidRepository.getUserId()))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}