package com.epamupskills.booknotes.di

import com.epamupskills.authorization.data.AccountRepositoryImpl
import com.epamupskills.authorization.domain.AccountRepository
import com.epamupskills.book_notes.data.repository.BooksRepositoryImpl
import com.epamupskills.book_notes.domain.BooksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindBooksRepository(repository: BooksRepositoryImpl): BooksRepository
}