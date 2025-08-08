package com.sarmad.moody.domain.repository

import com.sarmad.moody.data.local.entity.Mood
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    suspend fun insertMood(mood: Mood): Long

    suspend fun getMoodById(id: Int): Mood?

    fun getAllMoodsStream(): Flow<List<Mood>>

    suspend fun deleteMood(mood: Mood)

    suspend fun getAllWeatherTypes(): Flow<List<String>>

    fun getAllMoods(): List<Mood>
}
