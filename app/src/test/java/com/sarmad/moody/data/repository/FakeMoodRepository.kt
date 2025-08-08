package com.sarmad.moody.data.repository

import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.domain.repository.MoodRepository

class FakeMoodRepository : MoodRepository {
    val moods = mutableListOf<Mood>()
    var shouldFail = false
    private var nextId = 1L

    override suspend fun insertMood(mood: Mood): Long {
        return if (shouldFail) {
            -1L
        } else {
            val moodWithId = mood.copy(id = nextId.toInt())
            moods.add(moodWithId)
            nextId++
            moodWithId.id.toLong()
        }
    }

    override suspend fun getMoodById(id: Int): Mood? {
        return moods.find { it.id == id }
    }

    override fun getAllMoodsStream() = throw NotImplementedError()

    override suspend fun deleteMood(mood: Mood) {
        moods.removeIf { it.id == mood.id }
    }

    override suspend fun getAllWeatherTypes() = throw NotImplementedError()

    override fun getAllMoods(): List<Mood> = moods
}
