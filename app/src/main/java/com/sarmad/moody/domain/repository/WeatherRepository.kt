package com.sarmad.moody.domain.repository

import com.sarmad.moody.domain.dataholder.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): Flow<Result<Weather?>>
}
