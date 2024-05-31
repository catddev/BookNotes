package com.epamupskills.book_notes.presentation.search

sealed interface SearchBookUserIntent

data class Search(val input: String) : SearchBookUserIntent
data class ToggleBook(val bookId: String) : SearchBookUserIntent