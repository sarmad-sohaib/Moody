package com.sarmad.moody.domain.usecase.mood

import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.repository.mood.MoodRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface InsertMoodUseCase {
    suspend operator fun invoke(mood: Mood): Result<Long>
}

class DefaultInsertMoodUseCase @Inject constructor(
    private val moodRepository: MoodRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) : InsertMoodUseCase {

    override suspend fun invoke(mood: Mood) = withContext(
        context = dispatcherProvider.io
    ) {
        val savedMoodId = moodRepository.insertMood(mood = mood)

        savedMoodId.takeIf {
            it != -1L // bad: Magic number  (repo should return an error or throw in case of failure)
        }?.let {
            Result.success(value = it)
        } ?: run {
            Result.failure(
                // Good: An exception with custom message is thrown
                // Average: Base Exception is thrown instead of a custom or more specific exception
                exception = Throwable(
                    message = "Failed to insert mood with id: $savedMoodId"
                )
            )
        }
    }
}
