package com.epamupskills.book_notes.presentation.books.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.epamupskills.book_notes.R
import com.epamupskills.book_notes.databinding.ItemBookBinding
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.core.ImageLoader

class BookViewHolder(
    private val binding: ItemBookBinding,
    private val imageLoader: ImageLoader,
) : ViewHolder(binding.root) {

    fun bind(
        item: BookUi,
        onClickListener: (id: String) -> Unit,
        onBookmarkClickListener: (id: String) -> Unit
    ) {
        with(binding) {
            titleTextView.text = item.title
            authorTextView.text = item.authors
            descriptionTextView.text = item.description
            bindBookCover(item.thumbnailUrl)
            bindBookmarkState(item.isBookmarked)
            toggleBookImageButton.setOnClickListener {
                onBookmarkClickListener.invoke(item.id)
            }
            root.setOnClickListener {
                onClickListener.invoke(item.id)
            }
            //todo add user's rating? + change db version or reinstall
        }
    }

    private fun bindBookmarkState(isBookmarked: Boolean = false) {
        val icon =
            if (isBookmarked) R.drawable.icon_bookmark_filled else R.drawable.icon_bookmark_blank
        binding.toggleBookImageButton.setImageResource(icon)
    }

    private fun bindBookCover(url: String) {
        imageLoader.loadImage(
            into = binding.coverImageView,
            url = url,
            widthPx = binding.coverImageView.layoutParams.width
        )
    }
}