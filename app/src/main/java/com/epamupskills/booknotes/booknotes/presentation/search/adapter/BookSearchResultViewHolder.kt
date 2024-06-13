package com.epamupskills.booknotes.booknotes.presentation.search.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.epamupskills.booknotes.R
import com.epamupskills.booknotes.booknotes.presentation.models.BookUi
import com.epamupskills.booknotes.core.ImageLoader
import com.epamupskills.booknotes.databinding.ItemBookBinding

class BookSearchResultViewHolder(
    private val binding: ItemBookBinding,
    private val imageLoader: ImageLoader,
) : ViewHolder(binding.root) {

    fun bind(item: BookUi, onClickListener: (bookUi: BookUi) -> Unit) {
        with(binding) {
            titleTextView.text = item.title
            authorTextView.text = item.authors
            descriptionTextView.text = item.description
            bindBookCover(item.thumbnailUrl)
            bindBookmarkState(item.isBookmarked)
            toggleBookImageButton.setOnClickListener {
                onClickListener.invoke(item)
            }
        }
    }

    fun bindBookmarkState(isBookmarked: Boolean = false) {
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