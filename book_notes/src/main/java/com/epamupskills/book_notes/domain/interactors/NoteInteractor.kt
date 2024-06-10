package com.epamupskills.book_notes.domain.interactors

import com.epamupskills.book_notes.domain.models.Note
import com.epamupskills.book_notes.domain.usecases.CreateNoteUseCase
import com.epamupskills.book_notes.domain.usecases.GetNoteUseCase
import com.epamupskills.book_notes.domain.usecases.RemoveNoteUseCase
import com.epamupskills.book_notes.domain.usecases.UpdateBookWithNote
import com.epamupskills.book_notes.domain.usecases.UpdateNoteUseCase
import javax.inject.Inject

class NoteInteractor @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase,
    private val updateBookWithNote: UpdateBookWithNote,
) {

    fun getNote(noteId: Long) = getNoteUseCase.getNote(noteId)
    suspend fun createNote(note: Note) = createNoteUseCase.createNote(note)
    suspend fun updateNote(note: Note) = updateNoteUseCase.updateNote(note)
    suspend fun removeNote(noteId: Long) = removeNoteUseCase.removeNote(noteId)
    suspend fun updateBookWithNote(noteId: Long?, bookId: String) =
        updateBookWithNote.invoke(noteId, bookId)
}