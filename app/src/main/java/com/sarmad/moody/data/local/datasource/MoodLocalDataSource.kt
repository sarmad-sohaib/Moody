package com.sarmad.moody.data.local.datasource

import com.sarmad.moody.data.local.entity.Mood
import kotlinx.coroutines.flow.Flow

interface MoodLocalDataSource {
    suspend fun insertMood(mood: Mood): Long

    suspend fun getMoodById(id: String): Mood?

    fun getAllMoods(): Flow<List<Mood>>

    suspend fun deleteMood(mood: Mood)

    suspend fun getAllWeatherTypes(): Flow<List<String>>
}
