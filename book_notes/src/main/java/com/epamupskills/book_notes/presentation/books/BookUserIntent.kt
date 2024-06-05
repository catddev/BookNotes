package com.epamupskills.book_notes.presentation.books

sealed interface BookUserIntent

data class RemoveBook(val bookId: String): BookUserIntent
data class OpenBookNote(val bookId: String, val isTablet: Boolean): BookUserIntent //todo isTablet here?