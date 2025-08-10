package com.sarmad.moody.data.local.dao.mood

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarmad.moody.data.local.entity.mood.Mood
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertMood(mood: Mood): Long

    @Query(value = "SELECT * FROM Mood WHERE id = :id")
    suspend fun getMoodById(id: Int): Mood?

    @Query(value = "SELECT * FROM Mood")
    fun getAllMoodsStream(): Flow<List<Mood>>

    @Query(value = "SELECT * FROM Mood")
    fun getAllMoods(): List<Mood>

    @Delete
    suspend fun deleteMood(mood: Mood)

    @Query("SELECT DISTINCT weatherDescription FROM Mood")
    fun getUniqueWeatherDescriptions(): Flow<List<String>>
}