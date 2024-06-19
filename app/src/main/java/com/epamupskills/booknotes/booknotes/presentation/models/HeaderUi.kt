package com.epamupskills.booknotes.booknotes.presentation.models

import androidx.annotation.StringRes
import com.epamupskills.booknotes.booknotes.presentation.books.adapter.BooksAdapter.ItemTypes.TYPE_HEADER

data class HeaderUi(@StringRes val title: Int): BookListItem {

    override fun getItemType(): Int = TYPE_HEADER
}