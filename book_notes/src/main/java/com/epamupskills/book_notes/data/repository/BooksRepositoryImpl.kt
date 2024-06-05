package com.epamupskills.book_notes.data.repository

import com.epamupskills.book_notes.data.api.GoogleBooksApiService
import com.epamupskills.book_notes.data.db.BooksDao
import com.epamupskills.book_notes.data.mappers.BookDtoMapper
import com.epamupskills.book_notes.data.mappers.BookFromEntityMapper
import com.epamupskills.book_notes.data.mappers.BookToEntityMapper
import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.di.IO
import com.epamupskills.core.repository.UserCacheRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val api: GoogleBooksApiService,
    private val dao: BooksDao,
    private val bookFromEntityMapper: BookFromEntityMapper,
    private val bookToEntityMapper: BookToEntityMapper,
    private val bookDtoMapper: BookDtoMapper,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : BooksRepository, UserCacheRepository { //todo 2 interfaces bound with hilt to 1 impl is ok?

    override fun getAllBooks(userId: String): Flow<List<Book>> = dao.getAllBooks(userId)
        .map(bookFromEntityMapper::transformAll)
        .flowOn(dispatcherIo)

    override suspend fun addBook(book: Book, userId: String) = withContext(dispatcherIo) {
        dao.addBook(bookToEntityMapper.transform(book).copy(userId = userId))
    }

    override suspend fun removeBook(bookId: String, userId: String) = withContext(dispatcherIo) {
        dao.removeBook(userId = userId, bookId = bookId)
    }

    override suspend fun searchBooksByTitle(title: String, userId: String): List<Book> =
        withContext(dispatcherIo) {
            val books = api.getBooksByTitle(title)?.items
            bookDtoMapper.transformAll(books).map { book ->
                async {
                    book.copy(isBookmarked = dao.isBookSaved(userId = userId, book.id))
                }
            }.awaitAll()
        }

    override suspend fun updateBookWithNote(noteId: Int, userId: String, bookId: String) =
        withContext(dispatcherIo) {
            dao.updateBookWithNote(noteId = noteId, userId = userId, bookId = bookId)
        }

    override suspend fun clearCachedUserData(userId: String) = withContext(dispatcherIo) {
        dao.removeAllBooks(userId = userId)
    }
}