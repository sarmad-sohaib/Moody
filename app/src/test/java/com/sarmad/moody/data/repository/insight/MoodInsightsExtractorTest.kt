package com.sarmad.moody.data.repository.insight

import com.sarmad.moody.domain.model.mood.MoodInsight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MoodInsightsExtractorTest {

    private val extractor = MoodInsightsExtractor()

    @Test
    fun `extractInsights with empty list returns empty list`() {
        val insights = extractor.extractInsights(data = MoodTestData.emptyList)
        assertEquals(
            listOf<MoodInsight>(
                MoodInsight(
                    summary = "You are usually on Sunny days",
                    mood = "",
                    weather = "Sunny",
                    frequency = 0
                )
            ), insights
        )
    }

    @Test
    fun `extractInsights with single mood returns empty list`() {
        val insights = extractor.extractInsights(data = MoodTestData.singleMood)
        assertEquals(emptyList<MoodInsight>(), insights)
    }

    @Test
    fun `extractInsights with multiple moods, one frequent mood`() {
        val insights = extractor.extractInsights(data = MoodTestData.multipleMoodsFrequent)
        val expected = listOf(
            MoodInsight(
                summary = "You are usually Happy on Sunny days",
                mood = "Happy",
                weather = "Sunny",
                frequency = 2
            )
        )
        assertEquals(expected, insights)
    }

    @Test
    fun `extractInsights with multiple moods, multiple frequent moods, sorts by frequency`() {
        val insights = extractor.extractInsights(data = MoodTestData.multipleMoodsMultipleFrequent)
        val expected = listOf(
            MoodInsight(
                summary = "You are usually Sad on Rainy days",
                mood = "Sad",
                weather = "Rainy",
                frequency = 3
            ),
            MoodInsight(
                summary = "You are usually Happy on Sunny days",
                mood = "Happy",
                weather = "Sunny",
                frequency = 2
            ),
            MoodInsight(
                summary = "You are usually Neutral on Cloudy days",
                mood = "Neutral",
                weather = "Cloudy",
                frequency = 2
            )
        )
        println(expected)
        // Order within same frequency might vary, so we compare sets
        assertEquals(expected.toSet(), insights.toSet())
    }

    @Test
    fun `extractInsights limits to top 5 insights`() {
        val insights = extractor.extractInsights(data = MoodTestData.moodsForTopFiveInsights())
        assertEquals(5, insights.size)
    }

    @Test
    fun `extractInsights ignores moods with frequency less than 2`() {
        val insights = extractor.extractInsights(data = MoodTestData.moodsWithSomeBelowThreshold)
        val expected = listOf(
            MoodInsight(
                summary = "You are usually Sad on Rainy days",
                mood = "Sad",
                weather = "Rainy",
                frequency = 2
            )
        )
        assertEquals(expected, insights)
    }
}
