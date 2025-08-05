package com.sarmad.moody.data.repository.insight

import com.sarmad.moody.data.local.entity.Mood

object MoodTestData {
    val emptyList = emptyList<Mood>()

    val singleMood = listOf(
        Mood(id = 1, mood = "Happy", weatherDescription = "Sunny", createdAt = 0L, moodIcon = "")
    )

    val multipleMoodsFrequent = listOf(
        Mood(id = 1, mood = "Happy", weatherDescription = "Sunny", createdAt = 0L, moodIcon = ""),
        Mood(id = 2, mood = "Happy", weatherDescription = "Sunny", createdAt = 0L, moodIcon = ""),
        Mood(id = 3, mood = "Sad", weatherDescription = "Rainy", createdAt = 0L, moodIcon = "")
    )

    val multipleMoodsMultipleFrequent = listOf(
        Mood(id = 1, mood = "Happy", weatherDescription = "Sunny", createdAt = 0L, moodIcon = ""),
        Mood(id = 2, mood = "Happy", weatherDescription = "Sunny", createdAt = 0L, moodIcon = ""),
        Mood(id = 3, mood = "Sad", weatherDescription = "Rainy", createdAt = 0L, moodIcon = ""),
        Mood(id = 4, mood = "Sad", weatherDescription = "Rainy", createdAt = 0L, moodIcon = ""),
        Mood(id = 5, mood = "Sad", weatherDescription = "Rainy", createdAt = 0L, moodIcon = ""),
        Mood(
            id = 6,
            mood = "Neutral",
            weatherDescription = "Cloudy",
            createdAt = 0L,
            moodIcon = ""
        ),
        Mood(id = 7, mood = "Neutral", weatherDescription = "Cloudy", createdAt = 0L, moodIcon = "")
    )

    fun moodsForTopFiveInsights(): List<Mood> {
        val moods = mutableListOf<Mood>()
        for (i in 1..6) {
            moods.add(
                Mood(
                    id = (i * 2 - 1),
                    mood = "MoodA",
                    weatherDescription = "Weather$i",
                    createdAt = 0L,
                    moodIcon = ""
                )
            )
            moods.add(
                Mood(
                    id = (i * 2),
                    mood = "MoodA",
                    weatherDescription = "Weather$i",
                    createdAt = 0L,
                    moodIcon = ""
                )
            )
        }
        return moods
    }

    val moodsWithSomeBelowThreshold = listOf(
        Mood(id = 1, mood = "Happy", weatherDescription = "Sunny", createdAt = 0L, moodIcon = ""),
        Mood(id = 2, mood = "Sad", weatherDescription = "Rainy", createdAt = 0L, moodIcon = ""),
        Mood(id = 3, mood = "Sad", weatherDescription = "Rainy", createdAt = 0L, moodIcon = ""),
        Mood(id = 4, mood = "Neutral", weatherDescription = "Cloudy", createdAt = 0L, moodIcon = "")
    )
}
