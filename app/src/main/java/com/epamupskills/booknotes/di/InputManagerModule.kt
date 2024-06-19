package com.epamupskills.booknotes.di

import androidx.annotation.Keep
import com.epamupskills.booknotes.core.abstraction.InputManager
import com.epamupskills.booknotes.core.InputManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Keep
@Module
@InstallIn(ActivityRetainedComponent::class)
interface InputManagerModule {

    @Binds
    fun bindInputManager(inputManager: InputManagerImpl): InputManager
}