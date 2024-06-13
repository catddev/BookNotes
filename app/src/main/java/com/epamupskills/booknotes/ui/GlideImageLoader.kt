package com.epamupskills.booknotes.ui

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.epamupskills.booknotes.R
import com.epamupskills.booknotes.core.ImageLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GlideImageLoader @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageLoader {

    override fun loadImage(into: ImageView, url: String, widthPx: Int) {
        Glide.with(context)
            .load(url)
            .override(widthPx, 0)
            .error(R.drawable.icon_cover_placeholder)
            .into(into)
            .waitForLayout()
    }
}