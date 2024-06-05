package com.epamupskills.book_notes.domain.usecases

import com.epamupskills.book_notes.domain.BooksRepository
import com.epamupskills.book_notes.domain.models.Book
import com.epamupskills.core.repository.UidRepository
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