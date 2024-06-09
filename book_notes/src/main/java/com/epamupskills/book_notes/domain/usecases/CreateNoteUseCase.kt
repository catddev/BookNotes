package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.NotesRepository
import com.epamupskills.book_notes.domain.models.Note
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: NotesRepository,
) {

    suspend fun createNote(note: Note): Result<Long> = try {
        Result.success(repository.addNote(note))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}