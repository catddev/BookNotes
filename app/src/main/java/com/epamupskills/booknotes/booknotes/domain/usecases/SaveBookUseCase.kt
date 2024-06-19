package com.epamupskills.booknotes.booknotes.domain.usecases

import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.booknotes.domain.models.Book
import com.epamupskills.booknotes.core.abstraction.UidRepository
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(book: Book) = try {
        Result.success(booksRepository.addBook(book = book, userId = uidRepository.getUserId()))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}