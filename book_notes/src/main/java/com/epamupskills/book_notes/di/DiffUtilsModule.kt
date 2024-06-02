package com.epamupskills.book_notes.di

import com.epamupskills.book_notes.presentation.utils.BookDiffCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object DiffUtilsModule {

    @Provides
    fun provideBookDiffUtils(): BookDiffCallback = BookDiffCallback()
}