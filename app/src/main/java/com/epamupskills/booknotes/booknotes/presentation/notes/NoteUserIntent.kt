package com.epamupskills.booknotes.booknotes.presentation.notes

import com.epamupskills.booknotes.core.UserIntent

sealed interface NoteUserIntent : UserIntent
data class EditNote(val content: String) : NoteUserIntent
data object ClearNote : NoteUserIntent