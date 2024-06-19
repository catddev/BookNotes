package com.epamupskills.booknotes.booknotes.presentation.books.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.epamupskills.booknotes.booknotes.presentation.models.BookListItem
import com.epamupskills.booknotes.booknotes.presentation.models.BookUi
import com.epamupskills.booknotes.booknotes.presentation.models.HeaderUi
import com.epamupskills.booknotes.booknotes.presentation.utils.BookDiffUtil
import com.epamupskills.booknotes.core.abstraction.ImageLoader
import com.epamupskills.booknotes.databinding.ItemBookBinding
import com.epamupskills.booknotes.databinding.ItemHeaderBinding
import com.epamupskills.booknotes.di.Glide

class BooksAdapter(
    @Glide private val imageLoader: ImageLoader,
    private val onClickListener: (title: String, bookId: String) -> Unit,
    private val onBookmarkClickListener: (id: String) -> Unit,
) : ListAdapter<BookListItem, RecyclerView.ViewHolder>(BookDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_BOOK -> {
                val binding =
                    ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BookViewHolder(binding, imageLoader)
            }

            TYPE_HEADER -> {
                val binding =
                    ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }

            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookViewHolder -> {
                holder.bind(getItem(position) as BookUi, onClickListener, onBookmarkClickListener)
            }

            is HeaderViewHolder -> {
                holder.bind(getItem(position) as HeaderUi)
                setFullSpan(holder.itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).getItemType()

    private fun setFullSpan(view: View) {
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = true
    }

    companion object ItemTypes {
        const val TYPE_HEADER = 0
        const val TYPE_BOOK = 1
    }
}