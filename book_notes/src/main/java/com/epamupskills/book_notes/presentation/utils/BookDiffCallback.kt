package com.epamupskills.book_notes.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.epamupskills.book_notes.presentation.models.BookUi
import javax.inject.Inject

class BookDiffCallback @Inject constructor() : DiffUtil.ItemCallback<BookUi>() { //todo Z with list adapter -> async

    override fun areItemsTheSame(oldItem: BookUi, newItem: BookUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BookUi, newItem: BookUi): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: BookUi, newItem: BookUi): Any? = when {
        oldItem.isBookmarked != newItem.isBookmarked -> BookPayload.Bookmark(newItem.isBookmarked)
        else -> super.getChangePayload(oldItem, newItem)
    }
}