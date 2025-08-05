package com.sarmad.moody.domain.usecase.insights

import com.sarmad.moody.data.repository.insight.MoodInsightsExtractor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class GetInsightsUseCaseImplTest {

    private var moodInsightsExtractor: MoodInsightsExtractor? = null

    @BeforeEach
    fun setUp() {
        moodInsightsExtractor = MoodInsightsExtractor()
    }



    @AfterEach
    fun tearDown() {
        moodInsightsExtractor = null
    }
}
