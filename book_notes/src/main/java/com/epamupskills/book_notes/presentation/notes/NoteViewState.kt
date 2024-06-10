package com.epamupskills.book_notes.presentation.notes

import com.epamupskills.book_notes.presentation.models.NoteUi
import com.epamupskills.core.Constants.EMPTY

data class NoteViewState(
    val note: NoteUi = NoteUi(null, EMPTY),
)