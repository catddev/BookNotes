package com.epamupskills.booknotes.booknotes.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.epamupskills.booknotes.booknotes.presentation.models.BookUi
import com.epamupskills.booknotes.booknotes.presentation.models.BookListItem
import com.epamupskills.booknotes.booknotes.presentation.utils.BookDiffUtil
import com.epamupskills.booknotes.booknotes.presentation.utils.BookPayload
import com.epamupskills.booknotes.core.abstraction.ImageLoader
import com.epamupskills.booknotes.databinding.ItemBookBinding
import com.epamupskills.booknotes.di.Glide

class BookSearchResultsAdapter(
    @Glide private val imageLoader: ImageLoader,
    private val onClickListener: (book: BookUi) -> Unit,
) : ListAdapter<BookListItem, BookSearchResultViewHolder>(BookDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchResultViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookSearchResultViewHolder(binding, imageLoader)
    }

    override fun onBindViewHolder(
        holder: BookSearchResultViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (val latestPayload = payloads.lastOrNull()) {
            is BookPayload.Bookmark -> holder.bindBookmarkState(latestPayload.isBookMarked)
            else -> onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: BookSearchResultViewHolder, position: Int) {
        holder.bind(getItem(position) as BookUi) {
            onClickListener.invoke(getItem(position) as BookUi)
        }
    }
}