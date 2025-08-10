package com.sarmad.moody.domain.repository.weather

import com.sarmad.moody.domain.model.weather.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): Flow<Result<Weather?>>
}