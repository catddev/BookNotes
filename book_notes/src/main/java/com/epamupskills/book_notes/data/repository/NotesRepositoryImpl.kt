package com.epamupskills.book_notes.data.repository

import com.epamupskills.book_notes.data.db.NotesDao
import com.epamupskills.book_notes.data.mappers.NoteFromEntityMapper
import com.epamupskills.book_notes.data.mappers.NoteToEntityMapper
import com.epamupskills.book_notes.domain.NotesRepository
import com.epamupskills.book_notes.domain.models.Note
import com.epamupskills.core.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao,
    private val noteToEntityMapper: NoteToEntityMapper,
    private val noteFromEntityMapper: NoteFromEntityMapper,
    @IO private val dispatcherIo: CoroutineDispatcher
) : NotesRepository {

    override fun getNote(noteId: Long): Flow<Note?> =
        dao.getNote(noteId).filterNotNull().map(noteFromEntityMapper::transform)

    override suspend fun addNote(note: Note): Long = withContext(dispatcherIo) {
        dao.addNote(noteToEntityMapper.transform(note))
        //todo update book by id
        //todo add noteId to book entity - @Transaction in dao
    }

    override suspend fun removeNote(noteId: Long) = withContext(dispatcherIo) {
        dao.removeNote(noteId)
        //todo set noteId=null in book entity - @Transaction in dao
    }

    override suspend fun updateNote(noteId: Long, content: String) = withContext(dispatcherIo) {
        dao.updateNote(noteId, content)
    }
}