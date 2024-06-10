package com.epamupskills.book_notes.presentation.notes

import com.epamupskills.book_notes.presentation.models.NoteUi

data class NoteViewState(
    val note: NoteUi = NoteUi(null, ""),
)