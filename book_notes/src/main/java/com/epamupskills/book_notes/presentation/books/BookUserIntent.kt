package com.epamupskills.book_notes.presentation.books

import com.epamupskills.core.UserIntent

sealed interface BookUserIntent : UserIntent

data class RemoveBook(val bookId: String) : BookUserIntent
data class OpenBookNote(val bookId: String, val isTablet: Boolean) : BookUserIntent