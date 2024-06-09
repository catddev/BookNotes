package com.epamupskills.book_notes.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.NOTES_TABLE
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.NOTE_ID_COLUMN_NAME

@Entity(
    tableName = NOTES_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = [NOTE_ID_COLUMN_NAME],
            childColumns = [NOTE_ID_COLUMN_NAME],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long?,
    val content: String,
)