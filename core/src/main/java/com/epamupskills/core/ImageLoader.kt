package com.epamupskills.core

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(into: ImageView, url: String, widthPx: Int)
}