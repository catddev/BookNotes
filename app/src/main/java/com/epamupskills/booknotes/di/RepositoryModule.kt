package com.epamupskills.booknotes.di

import androidx.annotation.Keep
import com.epamupskills.booknotes.authorization.data.AccountRepositoryImpl
import com.epamupskills.booknotes.authorization.data.UidRepositoryImpl
import com.epamupskills.booknotes.authorization.domain.AccountRepository
import com.epamupskills.booknotes.booknotes.data.repository.BooksRepositoryImpl
import com.epamupskills.booknotes.booknotes.data.repository.NoteRepositoryImpl
import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.booknotes.domain.NoteRepository
import com.epamupskills.booknotes.core.abstraction.UidRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Keep
@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindBooksRepository(repository: BooksRepositoryImpl): BooksRepository

    @Binds
    fun bindUidRepository(repository: UidRepositoryImpl): UidRepository

    @Binds
    fun bindNoteRepository(repository: NoteRepositoryImpl): NoteRepository
}