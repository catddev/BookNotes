package com.epamupskills.booknotes.booknotes.presentation.books

import com.epamupskills.booknotes.core.abstraction.UserIntent

sealed interface BookUserIntent : UserIntent

data class RemoveBook(val bookId: String) : BookUserIntent
data class OpenBookNote(val bookTitle: String, val bookId: String) : BookUserIntent