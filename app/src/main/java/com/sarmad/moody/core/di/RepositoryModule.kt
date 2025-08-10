package com.sarmad.moody.core.di

import com.sarmad.moody.data.repository.mood.DefaultMoodRepository
import com.sarmad.moody.data.repository.weather.DefaultWeatherRepository
import com.sarmad.moody.data.repository.insight.DefaultInsightsRepository
import com.sarmad.moody.domain.repository.insights.InsightsRepository
import com.sarmad.moody.domain.repository.mood.MoodRepository
import com.sarmad.moody.domain.repository.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
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

    @Singleton
    @Binds
    abstract fun bindsInsightsRepository(
        defaultInsightsRepository: DefaultInsightsRepository,
    ): InsightsRepository
}
