package com.epamupskills.book_notes.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.epamupskills.book_notes.databinding.ItemBookBinding
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.book_notes.presentation.utils.BookDiffUtils
import com.epamupskills.core.ImageLoader
import com.epamupskills.core.di.Glide

class BookSearchResultsAdapter(
    @Glide private val imageLoader: ImageLoader,
    private val onClickListener: (id: String) -> Unit,
    private val diffUtils: BookDiffUtils,
) : RecyclerView.Adapter<BookSearchResultViewHolder>() {

    private val books = mutableListOf<BookUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchResultViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookSearchResultViewHolder(binding, imageLoader, onClickListener)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookSearchResultViewHolder, position: Int) {
        holder.onBind(books[position])
    }

    fun setBooks(newBooks: List<BookUi>) {
        diffUtils.setData(books, newBooks)
        val diffResult = DiffUtil.calculateDiff(diffUtils, false)
        books.clear()
        books.addAll(newBooks)
        diffResult.dispatchUpdatesTo(this)
    }
}