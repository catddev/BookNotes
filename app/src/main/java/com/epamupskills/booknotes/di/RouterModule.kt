package com.epamupskills.booknotes.di

import androidx.annotation.Keep
import com.epamupskills.booknotes.AppRouterImpl
import com.epamupskills.core.AppRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Keep
@Module
@InstallIn(ActivityComponent::class)
interface RouterModule {

    @Binds
    fun bindAppRouter(routerImpl: AppRouterImpl): AppRouter
}