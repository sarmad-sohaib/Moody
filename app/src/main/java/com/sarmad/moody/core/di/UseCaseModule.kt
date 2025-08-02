package com.sarmad.moody.core.di

import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.repository.MoodRepository
import com.sarmad.moody.domain.repository.WeatherRepository
import com.sarmad.moody.domain.usecase.mood.DefaultInsertMoodUseCase
import com.sarmad.moody.domain.usecase.mood.InsertMoodUseCase
import com.sarmad.moody.domain.usecase.weather.DefaultGetWeatherUseCase
import com.sarmad.moody.domain.usecase.weather.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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
}
