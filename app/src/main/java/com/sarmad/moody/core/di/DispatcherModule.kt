package com.sarmad.moody.core.di

import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.dispatcher.DefaultDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    abstract fun bindDispatcherProvider(
        impl: DefaultDispatcherProvider,
    ): CoroutineDispatcherProvider
}