package com.epamupskills.book_notes.presentation.books.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.epamupskills.book_notes.databinding.ItemBookBinding
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.book_notes.presentation.utils.BookDiffCallback
import com.epamupskills.book_notes.presentation.utils.BookPayload
import com.epamupskills.core.ImageLoader
import com.epamupskills.core.di.Glide

class BooksAdapter(
    @Glide private val imageLoader: ImageLoader,
    private val onClickListener: (id: String) -> Unit,
    private val onBookmarkClickListener: (id: String) -> Unit,
) : ListAdapter<BookUi, BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, imageLoader)
    }

    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (val latestPayload = payloads.lastOrNull()) {
            is BookPayload.Bookmark -> holder.bindBookmarkState(latestPayload.isBookMarked)
            else -> onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener, onBookmarkClickListener)
    }
}