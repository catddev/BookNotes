package com.epamupskills.booknotes.di

import androidx.annotation.Keep
import com.epamupskills.booknotes.authorization.data.AccountRepositoryImpl
import com.epamupskills.booknotes.authorization.data.UidRepositoryImpl
import com.epamupskills.booknotes.authorization.domain.AccountRepository
import com.epamupskills.booknotes.booknotes.data.repository.BooksRepositoryImpl
import com.epamupskills.booknotes.booknotes.data.repository.NoteRepositoryImpl
import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.booknotes.domain.NoteRepository
import com.epamupskills.booknotes.core.UidRepository
import com.epamupskills.booknotes.core.UserCacheRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Keep
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindBooksRepository(repository: BooksRepositoryImpl): BooksRepository

    @Binds
    fun bindUidRepository(repository: UidRepositoryImpl): UidRepository

    @Binds
    fun bindUserCacheRepository(repository: BooksRepositoryImpl): UserCacheRepository

    @Binds
    fun bindNoteRepository(repository: NoteRepositoryImpl): NoteRepository
}