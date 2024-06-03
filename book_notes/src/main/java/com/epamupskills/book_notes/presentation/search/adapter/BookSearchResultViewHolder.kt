package com.epamupskills.book_notes.presentation.search.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.epamupskills.book_notes.databinding.ItemBookBinding
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.core.ImageLoader

class BookSearchResultViewHolder(
    private val binding: ItemBookBinding,
    private val imageLoader: ImageLoader,
) : ViewHolder(binding.root) {

    fun bind(item: BookUi, onClickListener: (id: String) -> Unit) {
        with(binding) {
            imageLoader.loadImage(
                coverImageView,
                item.thumbnailUrl,
                coverImageView.layoutParams.width
            )

            bindBookmarkState(item.isBookmarked)
            toggleBookImageButton.setOnClickListener {
                onClickListener.invoke(item.id)
            }

            titleTextView.text = item.title
            authorTextView.text = item.authors
            descriptionTextView.text = item.description
        }
    }

    fun bindBookmarkState(isBookmarked: Boolean = false) {
        val icon =
            if (isBookmarked) com.epamupskills.core.R.drawable.icon_bookmark_filled else com.epamupskills.core.R.drawable.icon_bookmark_blank
        binding.toggleBookImageButton.setImageResource(icon)
    }
}