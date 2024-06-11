package com.epamupskills.book_notes.domain.models

import com.epamupskills.core.Constants.EMPTY

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val thumbnailUrl: String,
    val isBookmarked: Boolean = false,
    val note: String = EMPTY,
)