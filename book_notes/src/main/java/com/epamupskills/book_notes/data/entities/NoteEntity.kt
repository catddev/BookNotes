package com.epamupskills.book_notes.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.BOOK_ID_COLUMN_NAME
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.NOTES_TABLE
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.USER_ID_COLUMN_NAME

@Entity(
    tableName = NOTES_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = [USER_ID_COLUMN_NAME, BOOK_ID_COLUMN_NAME],
            childColumns = [USER_ID_COLUMN_NAME, BOOK_ID_COLUMN_NAME],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    val bookId: String,
    val userId: String,
    val content: String,
)