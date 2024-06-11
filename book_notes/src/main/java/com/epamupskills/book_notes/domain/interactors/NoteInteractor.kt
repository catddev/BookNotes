package com.epamupskills.book_notes.domain.interactors

import com.epamupskills.book_notes.domain.models.Note
import com.epamupskills.book_notes.domain.usecases.CheckBookExistsUseCase
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
    private val checkBookExistsUseCase: CheckBookExistsUseCase,
) {

    fun getNote(noteId: Long) = getNoteUseCase.invoke(noteId)
    suspend fun createNote(note: Note) = createNoteUseCase.invoke(note)
    suspend fun updateNote(note: Note) = updateNoteUseCase.invoke(note)
    suspend fun removeNote(noteId: Long) = removeNoteUseCase.invoke(noteId)
    suspend fun updateBookWithNote(noteId: Long?, bookId: String) =
        updateBookWithNote.invoke(noteId, bookId)
    suspend fun checkBookExists(noteId: Long) = checkBookExistsUseCase.invoke(noteId)
}