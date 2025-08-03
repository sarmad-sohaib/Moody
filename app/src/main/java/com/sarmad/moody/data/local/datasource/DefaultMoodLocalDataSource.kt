package com.sarmad.moody.data.local.datasource

import com.sarmad.moody.data.local.dao.MoodDao
import com.sarmad.moody.data.local.entity.Mood
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultMoodLocalDataSource @Inject constructor(
    private val moodDao: MoodDao,
) : MoodLocalDataSource {
    override suspend fun insertMood(mood: Mood) = moodDao.insertMood(mood)

    override suspend fun getMoodById(id: String) = moodDao.getMoodById(id)

    override fun getAllMoods() = moodDao.getAllMoods()

    override suspend fun deleteMood(mood: Mood) = moodDao.deleteMood(mood)
    override suspend fun getAllWeatherTypes(): Flow<List<String>> =
        moodDao.getUniqueWeatherDescriptions()
}
