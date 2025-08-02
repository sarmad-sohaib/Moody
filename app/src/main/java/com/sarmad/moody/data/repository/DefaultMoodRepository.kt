package com.sarmad.moody.data.repository

import com.sarmad.moody.data.local.dao.MoodDao
import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultMoodRepository @Inject constructor(
    private val moodDao: MoodDao,
) : MoodRepository {

    override suspend fun insertMood(mood: Mood) = moodDao.insertMood(mood)

    override suspend fun getMoodById(id: String): Mood? {
        return moodDao.getMoodById(id)
    }

    override fun getAllMoods(): Flow<List<Mood>> {
        return moodDao.getAllMoods()
    }

    override suspend fun deleteMood(mood: Mood) {
        moodDao.deleteMood(mood)
    }
}
