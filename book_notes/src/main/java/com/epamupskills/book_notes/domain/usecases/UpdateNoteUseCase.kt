package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.NotesRepository
import com.epamupskills.book_notes.domain.models.Note
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: NotesRepository,
) {

    suspend operator fun invoke(note: Note): Result<Unit> = try {
        Result.success(repository.updateNote(requireNotNull(note.noteId), note.content))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}