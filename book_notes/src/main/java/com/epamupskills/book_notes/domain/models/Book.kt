package com.epamupskills.book_notes.domain.models

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val thumbnailUrl: String,
    val isBookmarked: Boolean = false,
    val noteId: Long? = null,
)