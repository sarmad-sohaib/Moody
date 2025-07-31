package com.sarmad.moody.data.network.datasource

import com.sarmad.moody.data.core.dto.WeatherResponse

interface WeatherNetworkDataSource {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): Result<WeatherResponse>
}
