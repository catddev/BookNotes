package com.epamupskills.book_notes.data.mappers

import com.epamupskills.book_notes.data.entities.NoteEntity
import com.epamupskills.book_notes.domain.models.Note
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class NoteToEntityMapper @Inject constructor() : BaseMapper<Note, NoteEntity>() {

    override fun transform(input: Note): NoteEntity = NoteEntity(
        noteId = input.noteId,
        content = input.content
    )
}