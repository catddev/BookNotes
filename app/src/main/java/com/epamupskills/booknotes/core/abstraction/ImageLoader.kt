package com.epamupskills.booknotes.core.abstraction

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(into: ImageView, url: String, widthPx: Int)
}