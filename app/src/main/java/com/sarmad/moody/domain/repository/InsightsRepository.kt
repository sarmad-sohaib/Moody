package com.sarmad.moody.domain.repository

import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.domain.dataholder.MoodInsight

interface InsightsRepository {
    suspend fun getInsights(moods: List<Mood>): List<MoodInsight>
}
