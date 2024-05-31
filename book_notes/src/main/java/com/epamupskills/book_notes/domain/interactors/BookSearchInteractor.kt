package com.epamupskills.book_notes.domain.interactors

import com.epamupskills.book_notes.domain.usecases.RemoveBookUseCase
import com.epamupskills.book_notes.domain.usecases.SaveBookUseCase
import com.epamupskills.book_notes.domain.usecases.SearchBookUseCase
import javax.inject.Inject

class BookSearchInteractor @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase,
    private val saveBookUseCase: SaveBookUseCase,
    private val removeBookUseCase: RemoveBookUseCase,
) {

    suspend fun searchBooks(title: String) = searchBookUseCase.invoke(title)
}