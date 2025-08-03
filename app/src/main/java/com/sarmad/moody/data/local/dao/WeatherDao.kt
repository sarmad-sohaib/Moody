package com.sarmad.moody.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sarmad.moody.data.core.dto.Weather
import com.sarmad.moody.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weatherentity")
    fun getWeather(): Flow<WeatherEntity?>

    @Insert
    suspend fun insertWeather(weather: WeatherEntity): Long

    @Update
    suspend fun updateWeather(weather: WeatherEntity)
}
