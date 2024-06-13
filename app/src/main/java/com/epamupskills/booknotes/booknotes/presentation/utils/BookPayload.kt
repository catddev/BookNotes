package com.epamupskills.booknotes.booknotes.presentation.utils

sealed interface BookPayload {
    data class Bookmark(val isBookMarked: Boolean) : BookPayload
}