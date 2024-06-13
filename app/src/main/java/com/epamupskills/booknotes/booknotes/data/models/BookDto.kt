package com.epamupskills.booknotes.booknotes.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    @SerialName("id")
    val id: String,
    @SerialName("volumeInfo")
    val volumeInfo: BookVolumeDto,
)