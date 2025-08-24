package com.sarmad.moody.domain.repository.mood

import com.sarmad.moody.data.local.entity.mood.Mood
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    suspend fun insertMood(mood: Mood): Long

    suspend fun getMoodById(id: Int): Mood?

    fun getAllMoodsStream(): Flow<List<Mood>>

    suspend fun deleteMood(mood: Mood)

    suspend fun getAllWeatherTypes(): Flow<List<String>> // Weird: Why is this a part of mood repo

    fun getAllMoods(): List<Mood>
}