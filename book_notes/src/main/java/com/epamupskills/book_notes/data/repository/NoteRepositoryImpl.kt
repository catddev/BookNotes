package com.epamupskills.book_notes.data.repository

import com.epamupskills.book_notes.data.db.BooksDao
import com.epamupskills.book_notes.domain.NoteRepository
import com.epamupskills.core.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: BooksDao,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : NoteRepository {

    override suspend fun getNote(userId: String, bookId: String): String =
        withContext(dispatcherIo) {
            dao.getNote(userId = userId, bookId = bookId)
        }

    override suspend fun updateNote(note: String, userId: String, bookId: String) =
        withContext(dispatcherIo) {
            dao.updateBookWithNote(note = note, userId = userId, bookId = bookId)
        }
}