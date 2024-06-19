package com.epamupskills.booknotes.booknotes.presentation.models

sealed interface BookListItem {
    fun getItemType(): Int
}