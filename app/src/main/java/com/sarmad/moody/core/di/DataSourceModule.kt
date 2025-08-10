package com.sarmad.moody.core.di

import com.sarmad.moody.data.local.datasource.mood.DefaultMoodLocalDataSource
import com.sarmad.moody.data.local.datasource.mood.MoodLocalDataSource
import com.sarmad.moody.data.local.datasource.weather.RoomWeatherLocalDataSource
import com.sarmad.moody.data.local.datasource.weather.WeatherLocalDataSource
import com.sarmad.moody.data.network.datasource.weather.OpenWeatherMapDataSource
import com.sarmad.moody.data.network.datasource.weather.WeatherNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindWeatherDataSource(
        impl: OpenWeatherMapDataSource,
    ): WeatherNetworkDataSource

    @Binds
    abstract fun bindsMoodLocalDataSource(
        impl: DefaultMoodLocalDataSource,
    ): MoodLocalDataSource

    @Binds
    abstract fun bindsWeatherLocalDataSource(
        impl: RoomWeatherLocalDataSource,
    ): WeatherLocalDataSource
}
