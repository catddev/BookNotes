package com.epamupskills.book_notes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.BOOKS_TABLE
import com.epamupskills.book_notes.data.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM $BOOKS_TABLE WHERE userId=:userId")
    fun getAllBooks(userId: String): Flow<List<BookEntity>>

    @Query("SELECT isBookmarked FROM $BOOKS_TABLE WHERE userId=:userId AND bookId=:bookId")
    fun isBookSaved(userId: String, bookId: String): Boolean

    @Query("DELETE FROM $BOOKS_TABLE WHERE userId=:userId")
    suspend fun removeAllBooks(userId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book: BookEntity)

    @Query("DELETE FROM $BOOKS_TABLE WHERE userId=:userId AND bookId=:bookId")
    suspend fun removeBook(userId: String, bookId: String)
}