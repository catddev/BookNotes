package com.epamupskills.book_notes.presentation.models

data class BookUi(
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val thumbnailUrl: String,
    val isBookmarked: Boolean,
)