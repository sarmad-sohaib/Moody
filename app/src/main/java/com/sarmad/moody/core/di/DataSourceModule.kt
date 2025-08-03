package com.sarmad.moody.core.di

import com.sarmad.moody.data.local.datasource.DefaultMoodLocalDataSource
import com.sarmad.moody.data.local.datasource.MoodLocalDataSource
import com.sarmad.moody.data.local.datasource.RoomWeatherLocalDataSource
import com.sarmad.moody.data.local.datasource.WeatherLocalDataSource
import com.sarmad.moody.data.network.datasource.OpenWeatherMapDataSource
import com.sarmad.moody.data.network.datasource.WeatherNetworkDataSource
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
