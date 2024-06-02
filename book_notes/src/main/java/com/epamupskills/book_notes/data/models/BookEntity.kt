package com.epamupskills.book_notes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity()
@Serializable
data class BookEntity(
    @PrimaryKey
    @SerialName("id")
    val id: String,
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