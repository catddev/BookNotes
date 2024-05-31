package com.epamupskills.book_notes.domain.models

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String,
    val thumbnailUrl: String,
    val isAddedToCollection: Boolean = false,
)
