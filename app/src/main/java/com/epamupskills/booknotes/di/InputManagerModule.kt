package com.epamupskills.booknotes.di

import com.epamupskills.core.InputManager
import com.epamupskills.core.presentation.InputManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface InputManagerModule {

    @Binds
    fun bindInputManager(inputManager: InputManagerImpl): InputManager
}