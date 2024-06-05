package com.epamupskills.book_notes.presentation.books.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.epamupskills.book_notes.databinding.ItemHeaderBinding
import com.epamupskills.book_notes.presentation.models.HeaderUi

class HeaderViewHolder(
    private val binding: ItemHeaderBinding,
) : ViewHolder(binding.root) {

    fun bind(item: HeaderUi) {
        binding.headerTitle.text = binding.root.context.applicationContext.getString(item.title)
    }
}