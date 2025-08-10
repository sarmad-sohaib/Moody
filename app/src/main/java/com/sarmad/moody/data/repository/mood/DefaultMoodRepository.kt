package com.sarmad.moody.data.repository.mood

import com.sarmad.moody.data.local.datasource.mood.MoodLocalDataSource
import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.repository.mood.MoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultMoodRepository @Inject constructor(
    private val moodLocalDataSource: MoodLocalDataSource,
) : MoodRepository {

    override suspend fun insertMood(mood: Mood): Long = moodLocalDataSource.insertMood(mood)

    override suspend fun getMoodById(id: Int): Mood? {
        return moodLocalDataSource.getMoodById(id)
    }

    override fun getAllMoodsStream(): Flow<List<Mood>> = moodLocalDataSource.getAllMoodsStream()

    override suspend fun deleteMood(mood: Mood) {
        moodLocalDataSource.deleteMood(mood)
    }

    override suspend fun getAllWeatherTypes(): Flow<List<String>> = moodLocalDataSource.getAllWeatherTypes()

    override fun getAllMoods(): List<Mood> = moodLocalDataSource.getAllMoods()
}