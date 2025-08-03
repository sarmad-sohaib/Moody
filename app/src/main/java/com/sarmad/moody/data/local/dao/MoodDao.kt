package com.sarmad.moody.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarmad.moody.data.local.entity.Mood
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: Mood): Long

    @Query(value = "SELECT * FROM Mood WHERE id = :id")
    suspend fun getMoodById(id: String): Mood?

    @Query(value = "SELECT * FROM Mood")
    fun getAllMoods(): Flow<List<Mood>>

    @Delete
    suspend fun deleteMood(mood: Mood)

    @Query("SELECT DISTINCT weatherDescription FROM Mood")
    fun getUniqueWeatherDescriptions(): Flow<List<String>>
}
