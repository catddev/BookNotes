package com.epamupskills.booknotes.di

import androidx.annotation.Keep
import com.epamupskills.booknotes.core.ImageLoader
import com.epamupskills.booknotes.ui.GlideImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Qualifier

@Keep
@Module
@InstallIn(FragmentComponent::class)
interface ImageLoaderModule {

    @Glide
    @Binds
    fun bindGlideImageLoader(imageLoader: GlideImageLoader): ImageLoader
}

@Keep
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Glide