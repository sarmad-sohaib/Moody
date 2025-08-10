package com.sarmad.moody.domain.usecase.weather

import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeGetUniqueWeatherUseCase(
    private val dispatcherProvider: CoroutineDispatcherProvider,
) : GetUniqueWeatherTypesUseCase {

    private val weatherTypes = mutableListOf<String>()

    fun setWeatherTypes(weatherTypes: List<String>) {
        this.weatherTypes.clear()
        this.weatherTypes.addAll(weatherTypes)
    }

    override suspend fun invoke() = flow {
        emit(weatherTypes)
    }.flowOn(dispatcherProvider.io)
}
