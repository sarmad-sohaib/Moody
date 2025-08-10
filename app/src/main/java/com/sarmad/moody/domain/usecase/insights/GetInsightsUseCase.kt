package com.sarmad.moody.domain.usecase.insights

import com.sarmad.moody.domain.model.mood.MoodInsight
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.repository.insights.InsightsRepository
import com.sarmad.moody.domain.repository.mood.MoodRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetInsightsUseCase {
    suspend operator fun invoke(): List<MoodInsight>
}

class GetInsightsUseCaseImpl @Inject constructor(
    private val insightsRepository: InsightsRepository,
    private val moodsRepository: MoodRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) : GetInsightsUseCase {
    override suspend fun invoke(): List<MoodInsight> =
        withContext(dispatcherProvider.io) {
            val moods = moodsRepository.getAllMoods()
            insightsRepository.getInsights(moods = moods)
        }
}
