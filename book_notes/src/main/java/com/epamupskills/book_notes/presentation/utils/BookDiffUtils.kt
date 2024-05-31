package com.epamupskills.book_notes.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.epamupskills.book_notes.presentation.models.BookUi
import javax.inject.Inject

class BookDiffUtils @Inject constructor(): DiffUtil.Callback() {

    private var oldList: List<BookUi>? = null
    private var newList: List<BookUi>? = null

    override fun getOldListSize(): Int = oldList?.size ?: 0

    override fun getNewListSize(): Int = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList?.get(oldItemPosition) == newList?.get(newItemPosition)

    fun setData(oldList: List<BookUi>, newList: List<BookUi>) {
        this.oldList = oldList
        this.newList = newList
    }
}