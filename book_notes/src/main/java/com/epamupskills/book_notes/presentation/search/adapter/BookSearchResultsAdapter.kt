package com.epamupskills.book_notes.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.epamupskills.book_notes.databinding.ItemBookBinding
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.book_notes.presentation.utils.BookDiffCallback
import com.epamupskills.book_notes.presentation.utils.BookPayload
import com.epamupskills.core.ImageLoader
import com.epamupskills.core.di.Glide

class BookSearchResultsAdapter(
    @Glide private val imageLoader: ImageLoader,
    private val onClickListener: (id: String) -> Unit,
    diffUtils: BookDiffCallback,
) : ListAdapter<BookUi, BookSearchResultViewHolder>(diffUtils) {

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
        holder.bind(getItem(position)) { id ->
            onClickListener.invoke(id)
        }
    }
}