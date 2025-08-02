package com.sarmad.moody.domain.usecase.mood

import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.repository.MoodRepository
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
            it != -1L
        }?.let {
            Result.success(value = it)
        } ?: run {
            Result.failure(
                exception = Throwable(
                    message = "Failed to insert mood with id: $savedMoodId"
                )
            )
        }
    }
}
