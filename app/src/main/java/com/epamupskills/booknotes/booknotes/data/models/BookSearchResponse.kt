package com.epamupskills.booknotes.booknotes.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResponse(
    @SerialName("items")
    val items: List<BookDto>?
)