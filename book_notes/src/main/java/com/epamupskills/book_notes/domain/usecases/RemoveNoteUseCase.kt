package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.NotesRepository
import javax.inject.Inject

class RemoveNoteUseCase @Inject constructor(
    private val repository: NotesRepository,
) {
    suspend operator fun invoke(noteId: Long): Result<Unit> = try {
        Result.success(repository.removeNote(noteId))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}