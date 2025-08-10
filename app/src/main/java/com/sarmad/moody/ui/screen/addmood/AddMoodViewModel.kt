package com.sarmad.moody.ui.screen.addmood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmad.moody.R
import com.sarmad.moody.data.core.errortype.NetworkError
import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.model.weather.Weather
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.usecase.mood.InsertMoodUseCase
import com.sarmad.moody.domain.usecase.weather.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

data class AddMoodUiState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val userMsg: Int? = null,
)

@HiltViewModel
class AddMoodViewModel @Inject constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    val addMoodUseCase: InsertMoodUseCase,
    val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = AddMoodUiState())
    val uiState = _uiState.asStateFlow()

    fun getWeather(latitude: Double, longitude: Double) =
        viewModelScope.launch(context = dispatcherProvider.io) {
            _uiState.update { savedState ->
                savedState.copy(
                    isLoading = true
                )
            }

            getWeatherUseCase(
                latitude = latitude,
                longitude = longitude
            ).collect { weatherResult ->
                weatherResult.onSuccess { weather ->
                    _uiState.update { savedState ->
                        savedState.copy(
                            isLoading = false,
                            weather = weather,
                            userMsg = null
                        )
                    }
                }.onFailure { exception ->
                    _uiState.update { savedState ->
                        savedState.copy(
                            isLoading = false,
                            userMsg = exception.getUserReadableError(),
                        )
                    }
                }
            }
        }

    fun insertMood(mood: Mood) = viewModelScope.launch(
        context = dispatcherProvider.io
    ) {
        try {
            // throw an exception if any of the mood properties are invalid
            mood.validateMood()

            mood.apply {
                copy(
                    moodIcon = getMoodIcon(),
                ).apply {
                    addMoodUseCase(
                        mood = this,
                    ).onSuccess {
                        _uiState.update { savedState ->
                            savedState.copy(
                                userMsg = R.string.mood_saved_successfully,
                            )
                        }
                    }.onFailure { exception ->
                        _uiState.update { savedState ->
                            savedState.copy(
                                userMsg = exception.getUserReadableError(),
                            )
                        }
                    }
                }
            }
        } catch (_: IllegalArgumentException) {
            _uiState.update { savedState ->
                savedState.copy(
                    userMsg = R.string.mood_saving_error
                )
            }
        }
    }

    private fun Throwable.getUserReadableError() = when (this) {
        is NetworkError.Network -> R.string.network_error
        is NetworkError.Unauthorized -> R.string.network_error
        is NetworkError.NotFound -> R.string.weather_not_found_error
        is NetworkError.Server -> R.string.network_error
        is NetworkError.Unknown -> R.string.unknown_error
        else -> R.string.unknown_error
    }

    private fun Mood.validateMood() {
        require(mood.isNotBlank()) { "Mood cannot be blank" }
        require(weatherDescription.isNotBlank()) { "Weather description cannot be blank" }
        require(createdAt > 0) { "Created at timestamp must be positive" }
    }

    private fun Mood.getMoodIcon() = when (mood.lowercase(Locale.ROOT)) {
        "happy" -> "😊"
        "sad" -> "😢"
        "angry" -> "😠"
        "neutral" -> "😐"
        "excited" -> "😃"
        "anxious" -> "😟"
        "bored" -> "😒"
        else -> "😶"
    }

    fun userMsgShown() {
        _uiState.update { savedState ->
            savedState.copy(
                userMsg = null
            )
        }
    }
}
