package com.epamupskills.book_notes.data.repository

import com.epamupskills.book_notes.data.api.GoogleBooksApiService
import com.epamupskills.book_notes.data.mappers.BookDtoMapper
import com.epamupskills.book_notes.domain.BookSearchRepository
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoogleBooksRepository @Inject constructor(
    private val api: GoogleBooksApiService,
    private val mapper: BookDtoMapper,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : BookSearchRepository {

    override suspend fun getBooksByTitle(title: String): List<Book> =
        withContext(dispatcherIo) {
            mapper.transformAll(api.getBooksByTitle(title)?.items)
        }
}