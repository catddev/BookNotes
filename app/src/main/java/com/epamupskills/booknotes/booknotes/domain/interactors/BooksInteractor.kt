package com.epamupskills.booknotes.booknotes.domain.interactors

import com.epamupskills.booknotes.booknotes.domain.usecases.GetBooksUseCase
import com.epamupskills.booknotes.booknotes.domain.usecases.RemoveBookUseCase
import javax.inject.Inject

class BooksInteractor @Inject constructor(
    private val getBooks: GetBooksUseCase,
    private val removeBookUseCase: RemoveBookUseCase,
) {

    suspend fun getBooks() = getBooks.invoke()
    suspend fun removeBook(bookId: String) = removeBookUseCase.invoke(bookId)
}