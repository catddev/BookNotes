package com.epamupskills.book_notes.presentation.notes

import com.epamupskills.core.UserIntent

sealed interface NoteUserIntent : UserIntent
data class EditNote(val content: String) : NoteUserIntent
data object ClearNote : NoteUserIntent