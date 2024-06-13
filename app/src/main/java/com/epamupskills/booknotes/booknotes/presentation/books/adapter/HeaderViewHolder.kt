package com.epamupskills.booknotes.booknotes.presentation.books.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.epamupskills.booknotes.booknotes.presentation.models.HeaderUi
import com.epamupskills.booknotes.databinding.ItemHeaderBinding

class HeaderViewHolder(
    private val binding: ItemHeaderBinding,
) : ViewHolder(binding.root) {

    fun bind(item: HeaderUi) {
        binding.headerTitle.text = binding.root.context.applicationContext.getString(item.title)
    }
}