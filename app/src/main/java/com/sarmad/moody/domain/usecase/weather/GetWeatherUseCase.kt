package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.domain.dataholder.Weather
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): Flow<Result<Weather?>>
}
