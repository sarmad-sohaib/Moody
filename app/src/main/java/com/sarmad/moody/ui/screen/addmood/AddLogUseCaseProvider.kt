package com.sarmad.moody.ui.screen.addmood

import com.sarmad.moody.domain.usecase.mood.InsertMoodUseCase
import com.sarmad.moody.domain.usecase.weather.GetWeatherUseCase
import javax.inject.Inject

class AddLogUseCaseProvider @Inject constructor(
    val addMoodUseCase: InsertMoodUseCase,
    val getWeatherUseCase: GetWeatherUseCase,
)