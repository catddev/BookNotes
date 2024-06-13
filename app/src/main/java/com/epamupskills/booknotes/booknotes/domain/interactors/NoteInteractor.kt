package com.epamupskills.booknotes.booknotes.domain.interactors

import com.epamupskills.booknotes.booknotes.domain.usecases.CheckBookIsSavedUseCase
import com.epamupskills.booknotes.booknotes.domain.usecases.GetNoteUseCase
import com.epamupskills.booknotes.booknotes.domain.usecases.UpdateNoteUseCase
import javax.inject.Inject

class NoteInteractor @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val checkBookIsSavedUseCase: CheckBookIsSavedUseCase,
) {

    suspend fun getNote(bookId: String) = getNoteUseCase.invoke(bookId)
    suspend fun updateNote(note: String, bookId: String) = updateNoteUseCase.invoke(note, bookId)
    suspend fun checkBookIsSaved(bookId: String) = checkBookIsSavedUseCase.invoke(bookId)
}