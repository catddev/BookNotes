package com.epamupskills.booknotes.booknotes.presentation.models

import com.epamupskills.booknotes.booknotes.presentation.books.adapter.BooksAdapter.ItemTypes.TYPE_BOOK

data class BookUi(
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val thumbnailUrl: String,
    val isBookmarked: Boolean,
    val note: String,
) : BookListItem {

    val hasNote = note.isNotBlank()

    override fun getItemType(): Int = TYPE_BOOK
}