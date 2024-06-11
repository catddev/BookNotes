package com.epamupskills.book_notes.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.NOTES_TABLE
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.NOTE_ID_COLUMN_NAME

@Entity(
    tableName = NOTES_TABLE,
    indices = [Index(value = [NOTE_ID_COLUMN_NAME], unique = true)], //todo K
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = [NOTE_ID_COLUMN_NAME],
            childColumns = [NOTE_ID_COLUMN_NAME],
            onDelete = ForeignKey.CASCADE //todo why not deleting
        )
    ]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) //todo delete and just add note content column to book?
    val noteId: Long?,
    val content: String,
    val bookId: String,
    val userId: String,
)