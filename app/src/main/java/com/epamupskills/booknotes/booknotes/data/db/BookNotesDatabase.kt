package com.epamupskills.booknotes.booknotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epamupskills.booknotes.booknotes.data.db.BookNotesDatabaseSettings.VERSION
import com.epamupskills.booknotes.booknotes.data.entities.BookEntity

@Database(entities = [BookEntity::class], version = VERSION)
abstract class BookNotesDatabase : RoomDatabase() {

    abstract fun booksDao(): BooksDao
}