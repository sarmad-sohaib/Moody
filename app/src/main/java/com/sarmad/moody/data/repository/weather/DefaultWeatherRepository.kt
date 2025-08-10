package com.sarmad.moody.data.repository.weather

import com.sarmad.moody.core.util.TimeProvider
import com.sarmad.moody.core.util.isOlderThanOneHour
import com.sarmad.moody.core.util.toDomain
import com.sarmad.moody.core.util.toEntity
import com.sarmad.moody.data.local.datasource.weather.WeatherLocalDataSource
import com.sarmad.moody.data.network.datasource.weather.WeatherNetworkDataSource
import com.sarmad.moody.domain.repository.weather.WeatherRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val timeProvider: TimeProvider,
) : WeatherRepository {
    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ) = flow {
        weatherLocalDataSource.getWeather().collect { localWeather ->
            if (localWeather == null) { // weather being loaded very 1st time
                weatherNetworkDataSource.getWeather(
                    latitude = latitude,
                    longitude = longitude,
                ).onSuccess { weatherResponse ->
                    weatherLocalDataSource.saveWeather(
                        weatherEntity = weatherResponse.toEntity(
                            alreadySavedId = null,
                            currentTimeInMillis = timeProvider.currentTimeMillis()
                        )
                    )
                    emit(
                        value = Result.success(value = weatherResponse.toDomain())
                    )
                }
            } else if (localWeather.updatedAt.isOlderThanOneHour()) {
                weatherNetworkDataSource.getWeather(
                    latitude = latitude,
                    longitude = longitude,
                ).onSuccess { weatherResponse ->
                    emit(
                        value = Result.success(value = weatherResponse.toDomain())
                    )
                    weatherLocalDataSource.updateWeather(
                        weatherEntity = weatherResponse.toEntity(
                            alreadySavedId = localWeather.id,
                            currentTimeInMillis = timeProvider.currentTimeMillis()
                        )
                    )
                }.onFailure {
                    emit(
                        value = Result.failure(exception = Throwable(message = it.message))
                    )
                }
            } else {
                emit(
                    value = Result.success(value = localWeather.toDomain())
                )
            }
        }
    }
}