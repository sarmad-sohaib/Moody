package com.sarmad.moody.domain.repository

import com.sarmad.moody.data.local.entity.Mood
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    suspend fun insertMood(mood: Mood): Long

    suspend fun getMoodById(id: String): Mood?

    fun getAllMoods(): Flow<List<Mood>>

    suspend fun deleteMood(mood: Mood)
}
