package com.sarmad.moody.domain.usecase.mood

import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.data.repository.FakeMoodRepository
import com.sarmad.moody.domain.dispatcher.FakeDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultInsertMoodUseCaseTest {
    private lateinit var fakeMoodRepository: FakeMoodRepository
    private lateinit var insertMoodUseCase: InsertMoodUseCase

    @BeforeEach
    fun setUp() {
        fakeMoodRepository = FakeMoodRepository()
    }

    @Test
    fun `invoke returns success when mood is inserted`() = runTest {
        insertMoodUseCase = DefaultInsertMoodUseCase(
            moodRepository = fakeMoodRepository,
            dispatcherProvider = FakeDispatcherProvider(testScheduler)
        )

        val mood = Mood(
            id = 0, // will be ignored in fake
            mood = "Happy",
            weatherDescription = "Sunny",
            moodIcon = "🙂",
            createdAt = System.currentTimeMillis()
        )

        val result = insertMoodUseCase.invoke(mood)

        assertTrue(result.isSuccess)
        assertEquals(1L, result.getOrNull()) // Fake starts IDs from 1
        assertEquals(1, fakeMoodRepository.moods.size)
        assertEquals("Happy", fakeMoodRepository.moods.first().mood)
    }

    @Test
    fun `invoke returns failure when insert returns -1`() = runTest {
        insertMoodUseCase = DefaultInsertMoodUseCase(
            moodRepository = fakeMoodRepository,
            dispatcherProvider = FakeDispatcherProvider(testScheduler)
        )

        fakeMoodRepository.shouldFail = true
        val mood = Mood(
            id = 0,
            mood = "Sad",
            weatherDescription = "Rainy",
            moodIcon = "☹️",
            createdAt = System.currentTimeMillis()
        )

        val result = insertMoodUseCase.invoke(mood)

        assertTrue(result.isFailure)
        assertEquals(0, fakeMoodRepository.moods.size)
    }
}
