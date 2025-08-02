package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.domain.repository.WeatherRepository
import javax.inject.Inject

class DefaultGetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetWeatherUseCase {

    override suspend fun invoke(
        latitude: Double,
        longitude: Double,
    ): Result<WeatherResponse> {
        return weatherRepository.getWeather(
            latitude = latitude,
            longitude = longitude,
        )
    }
}
