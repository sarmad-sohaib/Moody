package com.sarmad.moody.data.repository

import com.sarmad.moody.data.local.datasource.MoodLocalDataSource
import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.domain.repository.MoodRepository
import javax.inject.Inject

class DefaultMoodRepository @Inject constructor(
    private val moodLocalDataSource: MoodLocalDataSource,
) : MoodRepository {

    override suspend fun insertMood(mood: Mood) = moodLocalDataSource.insertMood(mood)

    override suspend fun getMoodById(id: String): Mood? {
        return moodLocalDataSource.getMoodById(id)
    }

    override fun getAllMoods() = moodLocalDataSource.getAllMoods()

    override suspend fun deleteMood(mood: Mood) {
        moodLocalDataSource.deleteMood(mood)
    }

    override suspend fun getAllWeatherTypes() = moodLocalDataSource.getAllWeatherTypes()
}
