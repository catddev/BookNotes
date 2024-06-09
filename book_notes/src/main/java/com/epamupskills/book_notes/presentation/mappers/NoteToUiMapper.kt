package com.epamupskills.book_notes.presentation.mappers

import com.epamupskills.book_notes.domain.models.Note
import com.epamupskills.book_notes.presentation.models.NoteUi
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class NoteToUiMapper @Inject constructor() : BaseMapper<Note, NoteUi>() {

    override fun transform(input: Note): NoteUi = NoteUi(
        noteId = input.noteId,
        content = input.content
    )
}