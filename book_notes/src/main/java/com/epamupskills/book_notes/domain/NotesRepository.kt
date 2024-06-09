package com.epamupskills.book_notes.domain

import com.epamupskills.book_notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNote(noteId: Long): Flow<Note?>

    suspend fun addNote(note: Note): Long

    suspend fun removeNote(noteId: Long)

    suspend fun updateNote(noteId: Long, content: String)
}