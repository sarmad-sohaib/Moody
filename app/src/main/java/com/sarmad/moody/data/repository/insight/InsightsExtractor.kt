package com.sarmad.moody.data.repository.insight

import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.model.mood.MoodInsight
import javax.inject.Inject

interface InsightsExtractor<T, R> {
    fun extractInsights(data: List<T>): List<R>
}

class MoodInsightsExtractor @Inject constructor() : InsightsExtractor<Mood, MoodInsight> {
    override fun extractInsights(data: List<Mood>): List<MoodInsight> {
        return generateMoodWeatherInsights(moods = data)
    }

    private fun generateMoodWeatherInsights(moods: List<Mood>): List<MoodInsight> {
        if (moods.isEmpty()) return emptyList()

        // Group moods by weather → then by mood
        val moodWeatherCounts = moods
            .groupBy { it.weatherDescription }
            .mapValues { (_, list) ->
                list.groupingBy { it.mood }.eachCount()
            }

        val insights = moodWeatherCounts.mapNotNull { (weather, moodMap) ->
            val mostFrequentMoodEntry = moodMap.maxByOrNull { it.value }
            if (mostFrequentMoodEntry != null && mostFrequentMoodEntry.value >= 2) {
                val mood = mostFrequentMoodEntry.key
                val count = mostFrequentMoodEntry.value
                MoodInsight(
                    summary = "You are usually $mood on $weather days",
                    mood = mood,
                    weather = weather,
                    frequency = count
                )
            } else null
        }

        // Sort by frequency (descending), take top 5
        return insights.sortedByDescending { it.frequency }.take(5)
    }
}
