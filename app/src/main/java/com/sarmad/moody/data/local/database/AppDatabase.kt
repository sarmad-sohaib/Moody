package com.sarmad.moody.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sarmad.moody.data.local.dao.mood.MoodDao
import com.sarmad.moody.data.local.dao.weather.WeatherDao
import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.data.local.entity.weather.WeatherEntity

@Database(entities = [Mood::class, WeatherEntity::class], version = 4, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao

    abstract fun weatherDao(): WeatherDao
}
