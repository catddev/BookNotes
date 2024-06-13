package com.epamupskills.booknotes.booknotes.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookVolumeDto(
    @SerialName("title")
    val title: String,
    @SerialName("authors")
    val authors: List<String>? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("imageLinks")
    val imageLinks: ImageLinksDto? = null,
)