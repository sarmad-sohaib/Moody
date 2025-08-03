package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetUniqueWeatherTypesUseCase {
    suspend operator fun invoke(): Flow<List<String>>
}

class DefaultGetUniqueWeatherTypesUseCase @Inject constructor(
    private val moodRepository: MoodRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) : GetUniqueWeatherTypesUseCase {
    override suspend fun invoke(): Flow<List<String>> {
        return moodRepository.getAllWeatherTypes().flowOn(
            context = dispatcherProvider.io
        )
    }
}