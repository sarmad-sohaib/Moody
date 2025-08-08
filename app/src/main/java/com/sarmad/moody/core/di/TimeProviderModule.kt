package com.sarmad.moody.core.di

import com.sarmad.moody.core.util.DefaultTimeProvider
import com.sarmad.moody.core.util.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeProviderModule {

    @Binds
    abstract fun bindTimeProvider(
        defaultTimeProvider: DefaultTimeProvider,
    ): TimeProvider
}
