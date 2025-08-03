package com.sarmad.moody.domain.usecase.mood

import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllMoodsUseCase {
    suspend operator fun invoke(): Flow<List<Mood>>
}

class DefaultGetAllMoodsUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) : GetAllMoodsUseCase {
    override suspend fun invoke(): Flow<List<Mood>> {
        return moodRepository.getAllMoods()
    }
}