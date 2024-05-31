package com.epamupskills.book_notes.domain

import com.epamupskills.book_notes.domain.models.Book

interface BookSearchRepository {
    suspend fun getBooksByTitle(title: String): List<Book>
}