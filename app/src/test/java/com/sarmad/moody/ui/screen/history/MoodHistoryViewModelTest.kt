package com.sarmad.moody.ui.screen.history

import app.cash.turbine.test
import com.sarmad.moody.R
import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.dispatcher.FakeDispatcherProvider
import com.sarmad.moody.domain.usecase.mood.FakeGetAllMoodUseCase
import com.sarmad.moody.domain.usecase.weather.FakeGetUniqueWeatherUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MoodHistoryViewModelTest {

    private lateinit var fakeGetAllMoodsUseCase: FakeGetAllMoodUseCase
    private lateinit var fakeGetUniqueWeatherTypesUseCase: FakeGetUniqueWeatherUseCase
    private lateinit var dispatcherProvider: CoroutineDispatcherProvider
    private lateinit var viewModel: MoodHistoryViewModel
    private lateinit var scheduler: TestCoroutineScheduler

    private fun setupTestEnvironment() {
        scheduler = TestCoroutineScheduler()
        dispatcherProvider = FakeDispatcherProvider(scheduler = scheduler)
        fakeGetAllMoodsUseCase = FakeGetAllMoodUseCase(dispatcherProvider = dispatcherProvider)
        fakeGetUniqueWeatherTypesUseCase =
            FakeGetUniqueWeatherUseCase(dispatcherProvider = dispatcherProvider)
        viewModel = MoodHistoryViewModel(
            getAllMoodsUseCase = fakeGetAllMoodsUseCase,
            dispatcherProvider = dispatcherProvider,
            getUniqueWeatherTypesUseCase = fakeGetUniqueWeatherTypesUseCase
        )
    }

    @BeforeEach
    fun setup() {
        setupTestEnvironment()
    }

    @Test
    fun `isLoading false initially and true when getAllMoods is called`() =
        runTest(context = scheduler) {
            viewModel.uiState.test {
                val initialState = awaitItem()
                assertFalse(
                    initialState.isLoadingHistory,
                    "isLoadingHistory should be false initially"
                )

                viewModel.getAllMoods()

                val loadingState = awaitItem()
                assertTrue(
                    loadingState.isLoadingHistory,
                    "isLoadingHistory should be true when getAllMoods is called"
                )
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `empty mood list sets userMsg to no_moods_found`() = runTest(context = scheduler) {

        fakeGetAllMoodsUseCase.setMoods(moods = emptyList())
        viewModel.getAllMoods()

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        val moods = uiState.moods
        val userMsg = uiState.userMsg

        assertFalse(
            uiState.isLoadingHistory,
            "isLoadingHistory should be false"
        )
        assertEquals(
            emptyList<Mood>(),
            moods,
            "Moods should be empty"
        )
        assertEquals(
            R.string.no_moods_found,
            userMsg,
            "User message not set correctly"
        )
    }

    @Test
    fun `non-empty moods list updates moods and clears userMsg`() = runTest(context = scheduler) {

        val moodsData = listOf(
            Mood(
                id = 1,
                mood = "Happy",
                weatherDescription = "Sunny",
                moodIcon = "happy",
                createdAt = 1000L
            ),
            Mood(
                id = 2,
                mood = "Sad",
                weatherDescription = "Rainy",
                moodIcon = "sad",
                createdAt = 2000L
            )
        )
        fakeGetAllMoodsUseCase.setMoods(moods = moodsData)
        viewModel.getAllMoods()

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoadingHistory, "isLoadingHistory should be false")
        assertEquals(
            moodsData,
            uiState.moods,
            "Moods not updated correctly"
        )
        assertNull(uiState.userMsg, "User message should be null")
        assertEquals(
            moodsData,
            viewModel.allMoods,
            "allMoods not updated correctly"
        )
    }

    @Test
    fun `empty unique weather types list sets userMsg to no_weather_types_found`() =
        runTest(context = scheduler) {

            fakeGetUniqueWeatherTypesUseCase.setWeatherTypes(weatherTypes = emptyList())
            viewModel.getUniqueWeatherTypes()
            advanceUntilIdle()

            val finalState = viewModel.uiState.value
            assertFalse(
                finalState.isLoadingWeatherTypes,
                "isLoadingWeatherTypes should be false"
            )
            assertEquals(
                emptyList<String>(),
                finalState.weatherTypes,
                "Weather types should be empty"
            )
            assertEquals(
                R.string.no_weather_types_found,
                finalState.userMsg,
                "User message not set correctly"
            )
        }

    @Test
    fun `non-empty unique weather types list updates weatherTypes and clears userMsg`() =
        runTest(context = scheduler) {

            val weatherTypesData = listOf("Sunny", "Rainy")
            fakeGetUniqueWeatherTypesUseCase.setWeatherTypes(weatherTypes = weatherTypesData)
            viewModel.getUniqueWeatherTypes()
            advanceUntilIdle()

            val finalState = viewModel.uiState.value
            assertFalse(
                finalState.isLoadingWeatherTypes,
                "isLoadingWeatherTypes should be false"
            )
            assertEquals(
                weatherTypesData,
                finalState.weatherTypes,
                "Weather types not updated correctly"
            )
            assertNull(finalState.userMsg, "User message should be null")
        }

    @Test
    fun `filters moods by selected weather type`() = runTest(context = scheduler) {

        val moodsData = listOf(
            Mood(
                id = 1,
                mood = "Happy",
                weatherDescription = "Sunny",
                moodIcon = "happy",
                createdAt = 1000L
            ),
            Mood(
                id = 2,
                mood = "Sad",
                weatherDescription = "Rainy",
                moodIcon = "sad",
                createdAt = 2000L
            )
        )
        fakeGetAllMoodsUseCase.setMoods(moods = moodsData)
        viewModel.getAllMoods()
        advanceUntilIdle()

        viewModel.updateWeatherFilter(filter = "Sunny")

        val filteredMoods = viewModel.uiState.value.moods
        assertEquals(
            1,
            filteredMoods.size,
            "Filtered moods size incorrect"
        )
        assertEquals(
            "Sunny",
            filteredMoods[0].weatherDescription,
            "Filtered mood weather description incorrect"
        )
    }

    @Test
    fun `filtering by 'All' returns all moods`() = runTest(context = scheduler) {

        val moodsData = listOf(
            Mood(
                id = 1,
                mood = "Happy",
                weatherDescription = "Sunny",
                moodIcon = "happy",
                createdAt = 1000L
            )
        )
        fakeGetAllMoodsUseCase.setMoods(moods = moodsData)
        viewModel.getAllMoods()
        advanceUntilIdle()

        viewModel.updateWeatherFilter(filter = "All")

        assertEquals(
            moodsData,
            viewModel.uiState.value.moods,
            "Moods not returned correctly for 'All' filter"
        )
    }

    @Test
    fun `userMsgShown clears userMsg`() = runTest(context = scheduler) {
        viewModel.userMsgShown()
        assertNull(
            viewModel.uiState.value.userMsg,
            "User message should be cleared to null"
        )
    }
}
