package com.sarmad.moody.domain.usecase.mood

import com.sarmad.moody.data.local.entity.mood.Mood

class FakeInsertMoodUseCase : InsertMoodUseCase {
    var shouldFail = false
    private var nextId = 1L

    override suspend fun invoke(mood: Mood): Result<Long> {
        return if (shouldFail) {
            Result.failure(Throwable("Failed to insert mood with id: -1"))
        } else {
            val generatedId = nextId++
            Result.success(generatedId)
        }
    }
}