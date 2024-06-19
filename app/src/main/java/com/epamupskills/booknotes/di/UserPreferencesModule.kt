package com.epamupskills.booknotes.di

import com.epamupskills.booknotes.core.UserPreferencesImpl
import com.epamupskills.booknotes.core.abstraction.UserPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserPreferencesModule {

    @Binds
    fun bindUserPreferences(prefs: UserPreferencesImpl): UserPreferences
}