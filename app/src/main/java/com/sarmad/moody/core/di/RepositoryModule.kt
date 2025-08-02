package com.sarmad.moody.core.di

import com.sarmad.moody.data.repository.DefaultMoodRepository
import com.sarmad.moody.data.repository.DefaultWeatherRepository
import com.sarmad.moody.domain.repository.MoodRepository
import com.sarmad.moody.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsWeatherRepository(
        weatherRepositoryImpl: DefaultWeatherRepository,
    ): WeatherRepository

    @Singleton
    @Binds
    abstract fun bindsMoodRepository(
        moodRepositoryImpl: DefaultMoodRepository,
    ): MoodRepository
}
