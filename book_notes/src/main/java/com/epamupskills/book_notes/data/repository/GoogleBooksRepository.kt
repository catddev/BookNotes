package com.epamupskills.book_notes.data.repository

import com.epamupskills.book_notes.data.api.GoogleBooksApiService
import com.epamupskills.book_notes.data.mappers.BookDtoMapper
import com.epamupskills.book_notes.domain.BookSearchRepository
import com.epamupskills.book_notes.domain.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoogleBooksRepository @Inject constructor(
    private val api: GoogleBooksApiService,
    private val mapper: BookDtoMapper,
) : BookSearchRepository {

    override suspend fun getBooksByTitle(title: String): List<Book> =
        withContext(Dispatchers.IO) {
            mapper.transformAll(api.getBooksByTitle(title)?.items)
        }
}