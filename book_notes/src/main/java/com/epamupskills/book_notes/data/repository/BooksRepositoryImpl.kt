package com.epamupskills.book_notes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epamupskills.book_notes.data.api.GoogleBooksApiService
import com.epamupskills.book_notes.data.db.BooksDao
import com.epamupskills.book_notes.data.mappers.BookDtoMapper
import com.epamupskills.book_notes.data.mappers.BookEntityMapper
import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.PreferencesSettings
import com.epamupskills.core.PreferencesSettings.IS_ACCOUNT_DELETED_KEY
import com.epamupskills.core.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val api: GoogleBooksApiService,
    private val dao: BooksDao,
    private val dataStore: DataStore<Preferences>,
    private val entityMapper: BookEntityMapper,
    private val dtoMapper: BookDtoMapper,
    private val externalScope: CoroutineScope,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : BooksRepository {

    private val _userId = MutableStateFlow("")
    private val userId = _userId.asStateFlow()

    private val currentUserId = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey(PreferencesSettings.CURRENT_USER_ID_KEY)]
    }.flowOn(dispatcherIo)

    private val isAccountDeleted = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey(IS_ACCOUNT_DELETED_KEY)]
    }.flowOn(dispatcherIo)

    init {
        handleUserIdChange()
        handleAccountDeletion()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllBooks(): Flow<List<Book>> =
        userId
            .filterNot { it.isBlank() }
            .flatMapLatest { userId ->
                dao.getAllBooks(userId)
                    .map(entityMapper::mapAll)
                    .flowOn(dispatcherIo)
            }

    override suspend fun addBook(book: Book) = withContext(dispatcherIo) {
        dao.addBook(entityMapper.mapTo(book).copy(userId = userId.value))
    }

    override suspend fun removeBook(bookId: String) = withContext(dispatcherIo) {
        dao.removeBook(userId = userId.value, bookId = bookId)
    }

    override suspend fun removeAllBooks() = withContext(dispatcherIo) {
        dao.removeAllBooks(userId = userId.value)
    }

    override suspend fun searchBooksByTitle(title: String): List<Book> =
        withContext(dispatcherIo) {
            val books = api.getBooksByTitle(title)?.items
            dtoMapper.mapAll(books).map { book ->
                async {
                    book.copy(isBookmarked = dao.isBookSaved(userId = userId.value, book.id))
                }
            }.awaitAll()
        }

    private fun handleAccountDeletion() {
        externalScope.launch {
            isAccountDeleted.collect { isAccountDeleted ->
                if (isAccountDeleted == true) {
                    removeAllBooks()
                    dataStore.edit { prefs -> prefs.clear() }
                }
            }
        }
    }

    private fun handleUserIdChange() {
        externalScope.launch {
            currentUserId.collectLatest {
                if (!it.isNullOrBlank()) _userId.value = it
            }
        }
    }
}