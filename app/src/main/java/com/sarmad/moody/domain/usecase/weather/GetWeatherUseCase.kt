package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.domain.model.weather.Weather
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): Flow<Result<Weather?>>
}
