package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.NotesRepository
import com.epamupskills.book_notes.domain.models.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NotesRepository,
) {

    fun getNote(noteId: Long): Result<Flow<Note?>> = try {
        Result.success(repository.getNote(noteId))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}