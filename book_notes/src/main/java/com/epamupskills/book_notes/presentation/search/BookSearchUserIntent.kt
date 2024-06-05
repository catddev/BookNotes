package com.epamupskills.book_notes.presentation.search

import com.epamupskills.core.UserIntent

sealed interface SearchBookUserIntent : UserIntent

data class Search(val input: String) : SearchBookUserIntent
data class ToggleBookmark(val bookId: String) : SearchBookUserIntent