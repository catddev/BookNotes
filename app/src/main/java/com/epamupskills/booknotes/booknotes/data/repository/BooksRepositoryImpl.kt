package com.epamupskills.booknotes.booknotes.data.repository

import com.epamupskills.booknotes.booknotes.data.api.GoogleBooksApi
import com.epamupskills.booknotes.booknotes.data.db.BooksDao
import com.epamupskills.booknotes.booknotes.data.mappers.BookDtoMapper
import com.epamupskills.booknotes.booknotes.data.mappers.BookFromEntityMapper
import com.epamupskills.booknotes.booknotes.data.mappers.BookToEntityMapper
import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.booknotes.domain.models.Book
import com.epamupskills.booknotes.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val api: GoogleBooksApi,
    private val dao: BooksDao,
    private val bookFromEntityMapper: BookFromEntityMapper,
    private val bookToEntityMapper: BookToEntityMapper,
    private val bookDtoMapper: BookDtoMapper,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : BooksRepository {

    override fun getAllBooks(userId: String): Flow<List<Book>> = dao.getAllBooks(userId)
        .map(bookFromEntityMapper::transformAll)
        .flowOn(dispatcherIo)

    override suspend fun addBook(book: Book, userId: String) = withContext(dispatcherIo) {
        dao.addBook(bookToEntityMapper.transform(book).copy(userId = userId, isBookmarked = true))
    }

    override suspend fun removeBook(bookId: String, userId: String) = withContext(dispatcherIo) {
        dao.removeBook(userId = userId, bookId = bookId)
    }

    override suspend fun searchBooksByTitle(title: String) = withContext(dispatcherIo) {
        val result = api.getBooksByTitle(title)?.items
        bookDtoMapper.transformAll(result)
    }

    override fun isBookSaved(bookId: String, userId: String): Flow<Boolean> =
        dao.isBookSaved(bookId = bookId, userId = userId).flowOn(dispatcherIo)

    override suspend fun clearCachedUserData(userId: String) = withContext(dispatcherIo) {
        dao.removeAllBooks(userId = userId)
    }
}