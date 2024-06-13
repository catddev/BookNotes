package com.epamupskills.booknotes.booknotes.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageLinksDto(
    @SerialName("smallThumbnail")
    val smallThumbnail: String,
    @SerialName("thumbnail")
    val thumbnail: String,
)
