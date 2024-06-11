package com.epamupskills.book_notes.domain

interface NoteRepository {
    suspend fun getNote(userId: String, bookId: String): String
    suspend fun updateNote(note: String, userId: String, bookId: String)
}