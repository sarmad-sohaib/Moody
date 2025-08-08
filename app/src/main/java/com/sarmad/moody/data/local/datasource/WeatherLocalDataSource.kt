package com.sarmad.moody.data.local.datasource

import com.sarmad.moody.data.local.dao.WeatherDao
import com.sarmad.moody.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherLocalDataSource {
    fun getWeather(): Flow<WeatherEntity?>

    suspend fun saveWeather(weatherEntity: WeatherEntity): Long

    suspend fun updateWeather(weatherEntity: WeatherEntity)
}

class RoomWeatherLocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
) : WeatherLocalDataSource {
    override fun getWeather() = weatherDao.getWeather()

    override suspend fun saveWeather(weatherEntity: WeatherEntity): Long {
        return weatherDao.insertWeather(weather = weatherEntity)
    }

    override suspend fun updateWeather(weatherEntity: WeatherEntity) =
        weatherDao.updateWeather(weather = weatherEntity)

}
