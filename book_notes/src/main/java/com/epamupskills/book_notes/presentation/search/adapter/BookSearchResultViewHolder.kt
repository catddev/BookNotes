package com.epamupskills.book_notes.presentation.search.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.epamupskills.book_notes.databinding.ItemBookBinding
import com.epamupskills.book_notes.presentation.models.BookUi
import com.epamupskills.core.ImageLoader

class BookSearchResultViewHolder(
    private val binding: ItemBookBinding,
    private val imageLoader: ImageLoader,
    private val onClickListener: (id: String) -> Unit,
) : ViewHolder(binding.root) {

    fun onBind(item: BookUi) {
        with(binding) {
            imageLoader.loadImage(
                coverImageView,
                item.thumbnailUrl,
                coverImageView.layoutParams.width
            )

            toggleBookImageButton.apply {
                val icon =
                    if (item.isAddedToCollection) com.epamupskills.core.R.drawable.icon_bookmark_filled else com.epamupskills.core.R.drawable.icon_bookmark_blank

                setImageResource(icon)
                setOnClickListener {
                    onClickListener.invoke(item.id)
                }
            }

            titleTextView.text = item.title

            authorTextView.text =
                item.authors.joinToString(separator = SEPARATOR, postfix = "") { it }

            descriptionTextView.text = item.description
        }
    }

    companion object {
        private const val SEPARATOR = ", "
    }
}