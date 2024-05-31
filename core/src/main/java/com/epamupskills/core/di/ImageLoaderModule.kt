package com.epamupskills.core.di

import com.epamupskills.core.ImageLoader
import com.epamupskills.core.presentation.GlideImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Qualifier

@Module
@InstallIn(FragmentComponent::class)
interface ImageLoaderModule {

    @Glide
    @Binds
    fun bindGlideImageLoader(imageLoader: GlideImageLoader): ImageLoader
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Glide