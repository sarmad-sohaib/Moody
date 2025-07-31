package com.sarmad.moody.data.repository

import android.util.Log
import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.data.network.datasource.WeatherNetworkDataSource
import com.sarmad.moody.domain.repository.WeatherRepository
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
) : WeatherRepository {
    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): Result<WeatherResponse> {
        return weatherNetworkDataSource.getWeather(
            latitude = latitude,
            longitude = longitude,
        )
    }
}
