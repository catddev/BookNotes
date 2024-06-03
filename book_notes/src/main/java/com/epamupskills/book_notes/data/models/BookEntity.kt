package com.epamupskills.book_notes.data.models

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(primaryKeys = [])
@Serializable
data class BookEntity(
    @SerialName("book_id")
    val bookId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("title")
    val title: String,
    @SerialName("authors")
    val authors: List<String>,
    @SerialName("description")
    val description: String,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("isAddedToCollection")
    val isBookmarked: Boolean,
)