package com.sarmad.moody.data.repository.insight

import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.model.mood.MoodInsight
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.repository.insights.InsightsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultInsightsRepository @Inject constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val insightsExtractor: InsightsExtractor<Mood, MoodInsight>,
) : InsightsRepository {
    override suspend fun getInsights(moods: List<Mood>): List<MoodInsight> =
        withContext(context = dispatcherProvider.io) {
            insightsExtractor.extractInsights(
                data = moods
            )
        }
}
