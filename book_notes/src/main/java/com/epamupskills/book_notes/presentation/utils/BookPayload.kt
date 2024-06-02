package com.epamupskills.book_notes.presentation.utils

sealed interface BookPayload {
    data class Bookmark(val isBookMarked: Boolean) : BookPayload
}