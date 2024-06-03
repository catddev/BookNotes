package com.epamupskills.book_notes.domain

import com.epamupskills.book_notes.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getAllBooks(): Flow<List<Book>>
    suspend fun addBook(book: Book)
    suspend fun removeBook(bookId: String)
    suspend fun removeAllBooks()
    suspend fun searchBooksByTitle(title: String): List<Book>
}