package com.epamupskills.book_notes.presentation.books

import com.epamupskills.core.UserIntent

sealed interface BookUserIntent : UserIntent

data class RemoveBook(val bookId: String) : BookUserIntent
data class OpenBookNote(val bookTitle: String, val bookId: String) : BookUserIntent