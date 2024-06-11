package com.epamupskills.book_notes.domain

import com.epamupskills.book_notes.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getAllBooks(userId: String): Flow<List<Book>>
    suspend fun addBook(book: Book, userId: String)
    suspend fun removeBook(bookId: String, userId: String)
    suspend fun searchBooksByTitle(title: String): List<Book>
    suspend fun updateBookWithNote(noteId: Long?, userId: String, bookId: String)
    suspend fun doesBookExistByNote(noteId: Long): Boolean
}