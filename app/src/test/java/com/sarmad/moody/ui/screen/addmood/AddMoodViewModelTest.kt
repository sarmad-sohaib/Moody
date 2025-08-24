package com.sarmad.moody.ui.screen.addmood

import app.cash.turbine.test
import com.sarmad.moody.R
import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.dispatcher.FakeDispatcherProvider
import com.sarmad.moody.domain.model.weather.Weather
import com.sarmad.moody.domain.usecase.mood.FakeInsertMoodUseCase
import com.sarmad.moody.domain.usecase.weather.FakeGetWeatherUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class AddMoodViewModelTest {

    // excellent: tests testing only and only view model, both in names and in functionality
    private lateinit var dispatcherProvider: FakeDispatcherProvider
    private lateinit var insertMoodUseCase: FakeInsertMoodUseCase
    private lateinit var getWeatherUseCase: FakeGetWeatherUseCase
    private lateinit var viewModel: AddMoodViewModel
    private lateinit var scheduler: TestCoroutineScheduler

    @BeforeEach
    fun setup() {
        scheduler = TestCoroutineScheduler()
        dispatcherProvider = FakeDispatcherProvider(scheduler)
        insertMoodUseCase = FakeInsertMoodUseCase()
        getWeatherUseCase = FakeGetWeatherUseCase()
        viewModel = AddMoodViewModel(
            dispatcherProvider = dispatcherProvider,
            addMoodUseCase = insertMoodUseCase,
            getWeatherUseCase = getWeatherUseCase
        )
    }

    @Test
    fun `isLoading is initially false, and true when getWeather is called`() =
        runTest(context = scheduler) {
            viewModel.uiState.test {
                val initState = awaitItem()
                assertFalse(
                    initState.isLoading,
                    "isLoading should be false initially"
                )

                viewModel.getWeather(latitude = 10.0, longitude = 20.0)

                val updatedState = awaitItem()
                assertTrue(
                    updatedState.isLoading,
                    "isLoading should be true when getWeather is called until the result is received"
                )
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `getWeather emits loading then success`() = runTest(scheduler) {
        val expectedWeather = Weather(
            "London",
            25.0,
            description = "Rainy",
        )
        getWeatherUseCase.setWeatherResult(expectedWeather)

        viewModel.getWeather(latitude = 10.0, longitude = 20.0)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading, "isLoading should be false after weather is fetched")
        assertEquals(expectedWeather, state.weather, "Weather should be fetched")
        assertNull(state.userMsg, "userMsg should be null")
        assertEquals(10.0, getWeatherUseCase.lastLatitude, "Latitude should be passed to use case")
        assertEquals(
            20.0,
            getWeatherUseCase.lastLongitude,
            "Longitude should be passed to use case"
        )
    }

    @Test
    fun `getWeather emits loading then failure`() = runTest(scheduler) {
        val failingWeatherUseCase = FakeGetWeatherUseCase(shouldFail = true)
        viewModel = AddMoodViewModel(dispatcherProvider, insertMoodUseCase, failingWeatherUseCase)

        viewModel.getWeather(latitude = 1.0, longitude = 2.0)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading, "isLoading should be false after weather fetch fails")
        assertEquals(
            R.string.unknown_error,
            state.userMsg,
            "userMsg should be set to unknown error"
        )
    }

    @Test
    fun `insertMood with valid mood emits success`() = runTest(scheduler) {
        val mood = Mood(
            id = 0,
            mood = "happy",
            weatherDescription = "Sunny",
            createdAt = 123L,
            moodIcon = "",
        )
        viewModel.insertMood(mood = mood)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(
            R.string.mood_saved_successfully,
            state.userMsg,
            "userMsg should be set to mood saved successfully"
        )
    }

    @Test
    fun `insertMood with valid mood but use case fails emits error`() =
        runTest(context = scheduler) {
            insertMoodUseCase.shouldFail = true
            val mood = Mood(
                id = 0,
                mood = "happy",
                weatherDescription = "Sunny",
                createdAt = 123L,
                moodIcon = "",
            )
            viewModel.insertMood(mood = mood)
            advanceUntilIdle()
            val state = viewModel.uiState.value
            assertEquals(
                R.string.unknown_error,
                state.userMsg,
                "userMsg should be set to unknown error"
            )
        }

    @Test
    fun `insertMood with invalid mood emits saving error`() = runTest(context = scheduler) {
        val mood = Mood(
            id = 0,
            mood = "",
            weatherDescription = "Sunny",
            createdAt = 123L,
            moodIcon = "",
        )
        viewModel.insertMood(mood = mood)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(
            R.string.mood_saving_error,
            state.userMsg,
            "userMsg should be set to mood saving error"
        )
    }

    @Test
    fun `userMsgShown sets userMsg to null`() = runTest(context = scheduler) {
        val mood = Mood(
            id = 0,
            mood = "happy",
            weatherDescription = "Sunny",
            createdAt = 123L,
            moodIcon = "",
        )

        viewModel.insertMood(mood = mood)
        advanceUntilIdle()
        assertNotNull(viewModel.uiState.value.userMsg, "userMsg should not be null")

        viewModel.userMsgShown()
        assertNull(viewModel.uiState.value.userMsg, "userMsg should be null")
    }
}
