package com.sarmad.moody.data.local.datasource.weather

import com.sarmad.moody.data.local.entity.weather.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    fun getWeather(): Flow<WeatherEntity?>

    suspend fun saveWeather(weatherEntity: WeatherEntity): Long

    suspend fun updateWeather(weatherEntity: WeatherEntity)
}

