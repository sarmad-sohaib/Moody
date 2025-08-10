package com.sarmad.moody.data.local.datasource.weather

import com.sarmad.moody.data.local.entity.weather.WeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeWeatherLocalDataSource : WeatherLocalDataSource {
    private val weatherState = MutableStateFlow<WeatherEntity?>(null)
    private var lastId = 1L

    override fun getWeather(): Flow<WeatherEntity?> = weatherState.asStateFlow()

    override suspend fun saveWeather(weatherEntity: WeatherEntity): Long {
        weatherState.update { weatherEntity }
        return lastId++
    }

    override suspend fun updateWeather(weatherEntity: WeatherEntity) {
        weatherState.update { weatherEntity }
    }

    // Test utility
    fun setWeather(weather: WeatherEntity?) {
        weatherState.update { weather }
    }
}