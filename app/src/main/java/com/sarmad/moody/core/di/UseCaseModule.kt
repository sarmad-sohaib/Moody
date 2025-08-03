package com.sarmad.moody.core.di

import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.repository.MoodRepository
import com.sarmad.moody.domain.repository.WeatherRepository
import com.sarmad.moody.domain.usecase.mood.DefaultGetAllMoodsUseCase
import com.sarmad.moody.domain.usecase.mood.DefaultInsertMoodUseCase
import com.sarmad.moody.domain.usecase.mood.GetAllMoodsUseCase
import com.sarmad.moody.domain.usecase.mood.InsertMoodUseCase
import com.sarmad.moody.domain.usecase.weather.DefaultGetUniqueWeatherTypesUseCase
import com.sarmad.moody.domain.usecase.weather.DefaultGetWeatherUseCase
import com.sarmad.moody.domain.usecase.weather.GetUniqueWeatherTypesUseCase
import com.sarmad.moody.domain.usecase.weather.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Suppress("unused")
@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetWeatherUseCase(
        weatherRepository: WeatherRepository,
    ): GetWeatherUseCase {
        return DefaultGetWeatherUseCase(
            weatherRepository = weatherRepository,
        )
    }

    @Provides
    fun provideInsertMoodUseCase(
        moodRepository: MoodRepository,
        dispatcherProvider: CoroutineDispatcherProvider,
    ): InsertMoodUseCase {
        return DefaultInsertMoodUseCase(
            moodRepository = moodRepository,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Provides
    fun provideGetAllMoodsUseCase(
        moodRepository: MoodRepository,
    ): GetAllMoodsUseCase {
        return DefaultGetAllMoodsUseCase(
            moodRepository = moodRepository,
        )
    }

    @Provides
    fun provideGetUniqueWeatherTypesUseCase(
        moodRepository: MoodRepository,
        dispatcherProvider: CoroutineDispatcherProvider,
    ): GetUniqueWeatherTypesUseCase {
        return DefaultGetUniqueWeatherTypesUseCase(
            moodRepository = moodRepository,
            dispatcherProvider = dispatcherProvider,
        )
    }
}
