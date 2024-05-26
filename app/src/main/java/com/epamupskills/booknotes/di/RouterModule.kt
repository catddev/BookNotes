package com.epamupskills.booknotes.di

import com.epamupskills.booknotes.AppRouterImpl
import com.epamupskills.core.presentation.AppRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface RouterModule {

    @Binds
    fun bindAppRouter(routerImpl: AppRouterImpl): AppRouter
}