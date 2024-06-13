package com.epamupskills.booknotes.booknotes.domain.interactors

import com.epamupskills.booknotes.booknotes.domain.models.Book
import com.epamupskills.booknotes.booknotes.domain.usecases.GetBooksUseCase
import com.epamupskills.booknotes.booknotes.domain.usecases.RemoveBookUseCase
import com.epamupskills.booknotes.booknotes.domain.usecases.SaveBookUseCase
import com.epamupskills.booknotes.booknotes.domain.usecases.SearchBookUseCase
import javax.inject.Inject

class BookSearchInteractor @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase,
    private val saveBookUseCase: SaveBookUseCase,
    private val removeBookUseCase: RemoveBookUseCase,
    private val getBooksUseCase: GetBooksUseCase,
) {

    suspend fun getAllUserBooks() = getBooksUseCase.invoke()
    suspend fun searchBooks(title: String) = searchBookUseCase.invoke(title)
    suspend fun saveBook(book: Book) = saveBookUseCase.invoke(book)
    suspend fun removeBook(bookId: String) = removeBookUseCase.invoke(bookId)
}