package com.epamupskills.book_notes.presentation.mappers

import com.epamupskills.book_notes.domain.models.Note
import com.epamupskills.book_notes.presentation.models.NoteUi
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class NoteFromUiMapper @Inject constructor() : BaseMapper<NoteUi, Note>() {

    override fun transform(input: NoteUi): Note = Note(
        noteId = input.noteId,
        content = input.content
    )
}