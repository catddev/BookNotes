package com.epamupskills.book_notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.VERSION
import com.epamupskills.book_notes.data.entities.BookEntity
import com.epamupskills.book_notes.data.entities.NoteEntity

@Database(entities = [BookEntity::class, NoteEntity::class], version = VERSION)
abstract class BookNotesDatabase : RoomDatabase() {

    abstract fun booksDao(): BooksDao
    abstract fun notesDao(): NotesDao
}