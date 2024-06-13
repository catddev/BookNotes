package com.epamupskills.booknotes.di

import android.content.Context
import androidx.annotation.Keep
import androidx.room.Room
import com.epamupskills.booknotes.booknotes.data.db.BookNotesDatabase
import com.epamupskills.booknotes.booknotes.data.db.BookNotesDatabaseSettings.BOOK_NOTES_DATABASE_NAME
import com.epamupskills.booknotes.booknotes.data.db.BooksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Keep
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBookNotesDataBase(@ApplicationContext appContext: Context): BookNotesDatabase =
        Room.databaseBuilder(
            appContext,
            BookNotesDatabase::class.java,
            BOOK_NOTES_DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideBookDao(dataBase: BookNotesDatabase): BooksDao = dataBase.booksDao()
}