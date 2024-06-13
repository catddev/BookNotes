package com.epamupskills.booknotes.booknotes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epamupskills.booknotes.booknotes.data.db.BookNotesDatabaseSettings.BOOKS_TABLE
import com.epamupskills.booknotes.booknotes.data.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM $BOOKS_TABLE WHERE userId=:userId")
    fun getAllBooks(userId: String): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book: BookEntity)

    @Query("UPDATE $BOOKS_TABLE SET note=:note WHERE userId=:userId AND bookId=:bookId")
    suspend fun updateBookWithNote(note: String, userId: String, bookId: String)

    @Query("SELECT note FROM $BOOKS_TABLE WHERE userId=:userId AND bookId=:bookId")
    fun getNote(userId: String, bookId: String): String

    @Query("SELECT EXISTS(SELECT 1 FROM $BOOKS_TABLE WHERE userId=:userId AND bookId=:bookId LIMIT 1)")
    fun isBookSaved(userId: String, bookId: String): Flow<Boolean>

    @Query("DELETE FROM $BOOKS_TABLE WHERE userId=:userId")
    suspend fun removeAllBooks(userId: String)

    @Query("DELETE FROM $BOOKS_TABLE WHERE userId=:userId AND bookId=:bookId")
    suspend fun removeBook(userId: String, bookId: String)
}