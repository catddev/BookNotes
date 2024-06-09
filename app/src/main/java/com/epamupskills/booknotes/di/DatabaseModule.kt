package com.epamupskills.booknotes.di

import android.content.Context
import androidx.room.Room
import com.epamupskills.book_notes.data.db.BookNotesDatabase
import com.epamupskills.book_notes.data.db.BookNotesDatabaseSettings.BOOK_NOTES_DATABASE_NAME
import com.epamupskills.book_notes.data.db.BooksDao
import com.epamupskills.book_notes.data.db.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    fun provideNotesDao(dataBase: BookNotesDatabase): NotesDao = dataBase.notesDao()
}