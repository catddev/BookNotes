package com.epamupskills.book_notes.di

import com.epamupskills.book_notes.presentation.utils.BookDiffUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object DiffUtilsModule {

    @Provides
    fun provideBookDiffUtils(): BookDiffUtils = BookDiffUtils()
}