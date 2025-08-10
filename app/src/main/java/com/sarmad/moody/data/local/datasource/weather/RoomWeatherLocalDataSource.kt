package com.sarmad.moody.data.local.datasource.weather

import com.sarmad.moody.data.local.dao.weather.WeatherDao
import com.sarmad.moody.data.local.entity.weather.WeatherEntity
import javax.inject.Inject

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