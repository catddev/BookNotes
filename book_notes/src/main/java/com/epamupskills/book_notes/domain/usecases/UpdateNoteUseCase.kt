package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.NoteRepository
import com.epamupskills.core.repository.UidRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(note: String, bookId: String): Result<Unit> = try {
        Result.success(
            noteRepository.updateNote(
                note = note,
                bookId = bookId,
                userId = uidRepository.getUserId()
            )
        )
    } catch (t: Throwable) {
        Result.failure(t)
    }
}