package com.sarmad.moody.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sarmad.moody.data.local.dao.MoodDao
import com.sarmad.moody.data.local.entity.Mood

@Database(entities = [Mood::class], version = 3, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
}
