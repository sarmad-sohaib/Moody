package com.sarmad.moody.domain.repository.insights

import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.model.mood.MoodInsight

interface InsightsRepository {
    suspend fun getInsights(moods: List<Mood>): List<MoodInsight>
}