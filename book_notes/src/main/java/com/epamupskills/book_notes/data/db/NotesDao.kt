package com.epamupskills.book_notes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.NOTES_TABLE
import com.epamupskills.book_notes.data.entities.NoteEntity

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteEntity)

    @Query("DELETE FROM $NOTES_TABLE WHERE noteId=:noteId")
    suspend fun removeNote(noteId: Int)

    @Query("UPDATE $NOTES_TABLE SET content=:content WHERE noteId=:noteId")
    suspend fun updateNote(noteId: Int, content: String)
}