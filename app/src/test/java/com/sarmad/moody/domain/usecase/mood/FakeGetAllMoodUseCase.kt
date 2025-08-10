package com.sarmad.moody.domain.usecase.mood

import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeGetAllMoodUseCase(
    private val dispatcherProvider: CoroutineDispatcherProvider,
) : GetAllMoodsUseCase {

    private val moodsStream = mutableListOf<Mood>()

    fun setMoods(moods: List<Mood>): Boolean {
        moodsStream.clear()
        return moodsStream.addAll(moods)
    }

    override suspend fun invoke() = flow {
        emit(moodsStream)
    }.flowOn(dispatcherProvider.io)
}
