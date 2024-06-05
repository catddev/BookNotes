package com.epamupskills.book_notes.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.book_notes.presentation.models.HeaderUi
import com.epamupskills.book_notes.presentation.models.BookListItem
import javax.inject.Inject

class BookDiffCallback @Inject constructor() : DiffUtil.ItemCallback<BookListItem>() {

    override fun areItemsTheSame(oldItem: BookListItem, newItem: BookListItem): Boolean = when {
        oldItem is BookUi && newItem is BookUi -> oldItem.id == newItem.id
        oldItem is HeaderUi && newItem is HeaderUi -> oldItem.title == newItem.title
        else -> false
    }

    override fun areContentsTheSame(oldItem: BookListItem, newItem: BookListItem): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: BookListItem, newItem: BookListItem): Any? = when {
        oldItem is BookUi && newItem is BookUi && oldItem.isBookmarked != newItem.isBookmarked ->
            BookPayload.Bookmark(newItem.isBookmarked)

        else -> super.getChangePayload(oldItem, newItem)
    }
}