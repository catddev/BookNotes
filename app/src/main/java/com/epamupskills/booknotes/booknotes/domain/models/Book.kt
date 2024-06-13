package com.epamupskills.booknotes.booknotes.domain.models

import com.epamupskills.booknotes.core.Constants.EMPTY

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val thumbnailUrl: String,
    val isBookmarked: Boolean = false,
    val note: String = EMPTY,
)