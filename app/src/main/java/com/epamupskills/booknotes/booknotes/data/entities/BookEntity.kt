package com.epamupskills.booknotes.booknotes.data.entities

import androidx.room.Entity
import com.epamupskills.booknotes.booknotes.data.db.BookNotesDatabaseSettings.BOOKS_TABLE
import com.epamupskills.booknotes.booknotes.data.db.BookNotesDatabaseSettings.BOOK_ID_COLUMN_NAME
import com.epamupskills.booknotes.booknotes.data.db.BookNotesDatabaseSettings.USER_ID_COLUMN_NAME

@Entity(
    tableName = BOOKS_TABLE,
    primaryKeys = [USER_ID_COLUMN_NAME, BOOK_ID_COLUMN_NAME],
)
data class BookEntity(
    val bookId: String,
    val userId: String = "",
    val title: String,
    val authors: String,
    val description: String,
    val thumbnailUrl: String,
    val isBookmarked: Boolean,
    val note: String,
)