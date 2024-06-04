package com.epamupskills.book_notes.domain.interactors

import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.book_notes.domain.usecases.GetBooksUseCase
import com.epamupskills.book_notes.domain.usecases.RemoveBookUseCase
import com.epamupskills.book_notes.domain.usecases.SaveBookUseCase
import javax.inject.Inject

class BooksInteractor @Inject constructor(
    private val getBooks: GetBooksUseCase,
    private val saveBookUseCase: SaveBookUseCase,
    private val removeBookUseCase: RemoveBookUseCase,
) {

    suspend fun getBooks() = getBooks.invoke()
    suspend fun saveBook(book: Book) = saveBookUseCase.invoke(book)
    suspend fun removeBook(bookId: String) = removeBookUseCase.invoke(bookId)
}