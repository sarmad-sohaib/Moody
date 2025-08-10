package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.domain.repository.weather.WeatherRepository
import javax.inject.Inject

class DefaultGetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetWeatherUseCase {

    override suspend fun invoke(
        latitude: Double,
        longitude: Double,
    ) = weatherRepository.getWeather(
        latitude = latitude,
        longitude = longitude,
    )
}
