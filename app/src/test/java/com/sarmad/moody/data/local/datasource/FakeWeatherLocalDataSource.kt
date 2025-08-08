package com.sarmad.moody.data.local.datasource

import com.sarmad.moody.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeWeatherLocalDataSource : WeatherLocalDataSource {
    private val weatherState = MutableStateFlow<WeatherEntity?>(null)
    private var lastId = 1L

    override fun getWeather(): Flow<WeatherEntity?> = weatherState.asStateFlow()

    override suspend fun saveWeather(weatherEntity: WeatherEntity): Long {
        weatherState.value = weatherEntity
        return lastId++
    }

    override suspend fun updateWeather(weatherEntity: WeatherEntity) {
        weatherState.value = weatherEntity
    }

    // Test utility
    fun setWeather(weather: WeatherEntity?) {
        weatherState.update { weather }
    }
}
