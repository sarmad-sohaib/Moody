package com.sarmad.moody.domain.usecase.insights

import com.sarmad.moody.domain.model.mood.MoodInsight

class FakeGetInsightsUseCase : GetInsightsUseCase {

    private val insights = mutableListOf<MoodInsight>()

    fun setInsights(insights: List<MoodInsight>) {
        this.insights.clear()
        this.insights.addAll(insights)
    }

    override suspend fun invoke(): List<MoodInsight> {
        return insights
    }
}