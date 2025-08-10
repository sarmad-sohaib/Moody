package com.sarmad.moody.ui.screen.insights

import app.cash.turbine.test
import com.sarmad.moody.R
import com.sarmad.moody.domain.model.mood.MoodInsight
import com.sarmad.moody.domain.dispatcher.FakeDispatcherProvider
import com.sarmad.moody.domain.usecase.insights.FakeGetInsightsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InsightsViewModelTest {

    private lateinit var testScheduler: TestCoroutineScheduler
    private lateinit var dispatchers: FakeDispatcherProvider
    private lateinit var fakeGetInsightsUseCase: FakeGetInsightsUseCase
    private lateinit var viewModel: InsightsViewModel

    @BeforeEach
    fun setUp() {
        testScheduler = TestCoroutineScheduler()
        dispatchers = FakeDispatcherProvider(scheduler = testScheduler)
        fakeGetInsightsUseCase = FakeGetInsightsUseCase()
        viewModel = InsightsViewModel(
            dispatchersProviders = dispatchers,
            getInsightsUseCase = fakeGetInsightsUseCase
        )
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.insights.isEmpty())
        assertNull(state.userMsg)
    }

    @Test
    fun `fetchInsights sets isLoading true immediately`() = runTest {
        val fakeInsightsUseCase = FakeGetInsightsUseCase()
        val viewModel = InsightsViewModel(
            dispatchersProviders = FakeDispatcherProvider(testScheduler),
            getInsightsUseCase = fakeInsightsUseCase
        )
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertFalse(initialState.isLoading)

            viewModel.fetchInsights()

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    @DisplayName("When successful with insights, updates state correctly")
    fun `populates insights and sets isLoading to false on successful fetch`() =
        runTest(testScheduler) {
            val mockInsights = listOf(
                MoodInsight("Summary 1", "Happy", "Sunny", 5),
                MoodInsight("Summary 2", "Sad", "Rainy", 3)
            )
            fakeGetInsightsUseCase.setInsights(mockInsights)

            viewModel.fetchInsights()
            testScheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            assertFalse(state.isLoading)
            assertEquals(listOf("Summary 1", "Summary 2"), state.insights)
            assertNull(state.userMsg)
        }

    @Test
    @DisplayName("When successful with empty list, sets 'no insights' message")
    fun `sets user message when no insights are found`() = runTest(testScheduler) {
        fakeGetInsightsUseCase.setInsights(emptyList())

        viewModel.fetchInsights()
        testScheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.insights.isEmpty())
        assertEquals(R.string.no_insights_found, state.userMsg)
    }

    @Test
    @DisplayName("Clears user message after it has been shown")
    fun `clears user message`() = runTest(testScheduler) {
        fakeGetInsightsUseCase.setInsights(emptyList())
        viewModel.fetchInsights()
        testScheduler.advanceUntilIdle()
        assertEquals(R.string.no_insights_found, viewModel.uiState.value.userMsg)

        viewModel.userMsgShown()

        val state = viewModel.uiState.value
        assertNull(state.userMsg)
    }

    @Test
    @DisplayName("Does not change state if user message is already null")
    fun `does not change state if user message is null`() = runTest(testScheduler) {
        val initialState = viewModel.uiState.value
        assertNull(initialState.userMsg)

        viewModel.userMsgShown()

        assertEquals(initialState, viewModel.uiState.value)
    }
}
