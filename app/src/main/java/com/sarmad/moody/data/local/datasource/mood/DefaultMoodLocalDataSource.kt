package com.sarmad.moody.data.local.datasource.mood

import com.sarmad.moody.data.local.dao.mood.MoodDao
import com.sarmad.moody.data.local.entity.mood.Mood
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultMoodLocalDataSource @Inject constructor(
    private val moodDao: MoodDao,
) : MoodLocalDataSource {
    override suspend fun insertMood(mood: Mood) = moodDao.insertMood(mood)

    override suspend fun getMoodById(id: Int) = moodDao.getMoodById(id)

    override fun getAllMoodsStream() = moodDao.getAllMoodsStream()

    override suspend fun deleteMood(mood: Mood) = moodDao.deleteMood(mood)
    override suspend fun getAllWeatherTypes(): Flow<List<String>> =
        moodDao.getUniqueWeatherDescriptions()

    override fun getAllMoods() = moodDao.getAllMoods()
}
