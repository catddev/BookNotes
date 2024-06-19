package com.epamupskills.booknotes.booknotes.presentation.search

import com.epamupskills.booknotes.booknotes.presentation.models.BookUi
import com.epamupskills.booknotes.core.abstraction.UserIntent

sealed interface SearchBookUserIntent : UserIntent

data class Search(val input: String) : SearchBookUserIntent
data class ToggleBookmark(val book: BookUi) : SearchBookUserIntent