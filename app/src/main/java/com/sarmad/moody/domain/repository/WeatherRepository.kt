package com.sarmad.moody.domain.repository

import com.sarmad.moody.data.core.dto.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): Result<WeatherResponse>
}
