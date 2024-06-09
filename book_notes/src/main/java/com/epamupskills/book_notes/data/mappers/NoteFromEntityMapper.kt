package com.epamupskills.book_notes.data.mappers

import com.epamupskills.book_notes.data.entities.NoteEntity
import com.epamupskills.book_notes.domain.models.Note
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class NoteFromEntityMapper @Inject constructor() : BaseMapper<NoteEntity, Note>() {

    override fun transform(input: NoteEntity): Note = Note(
        noteId = input.noteId,
        content = input.content
    )
}