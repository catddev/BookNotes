package com.epamupskills.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Default
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IO
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Main
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Unconfined
    @Provides
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}

@Retention(AnnotationRetention.BINARY) //todo Z
@Qualifier
annotation class Default

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IO

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Main

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Unconfined