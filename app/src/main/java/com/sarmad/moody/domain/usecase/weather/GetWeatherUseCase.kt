package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.data.core.dto.WeatherResponse

interface GetWeatherUseCase {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): Result<WeatherResponse>
}
