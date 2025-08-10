package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.domain.model.weather.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FakeGetWeatherUseCase(
    val shouldFail: Boolean = false,
) : GetWeatherUseCase {

    private var weatherResult = MutableStateFlow<Weather?>(null)
    var lastLatitude: Double? = null
        private set
    var lastLongitude: Double? = null
        private set

    fun setWeatherResult(weather: Weather) {
        this.weatherResult.update {
            weather
        }
    }

    override suspend fun invoke(latitude: Double, longitude: Double) = flow {
        lastLatitude = latitude
        lastLongitude = longitude

        if (shouldFail) {
            emit(Result.failure(Throwable("Failed to get weather")))
        } else {
            emit(Result.success(weatherResult.value))
        }

    }
}
