package com.sarmad.moody.ui.screen.addmood

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

data class AddMoodUiState(
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val userMsg: String? = null,
)

@HiltViewModel
class AddMoodViewModel @Inject constructor(
    private val addLogUseCaseProvider: AddLogUseCaseProvider,
    private val dispatcherProvider: CoroutineDispatcherProvider,
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

            addLogUseCaseProvider.getWeatherUseCase(
                latitude = latitude,
                longitude = longitude
            ).onSuccess { weather ->
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
                        userMsg = exception.message,
                    )
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
                    Log.d("TAG", "insertMood: $this")
                    addLogUseCaseProvider.addMoodUseCase(
                        mood = this,
                    ).onSuccess {
                        _uiState.update { savedState ->
                            savedState.copy(
                                userMsg = "Mood added successfully!",
                            )
                        }
                    }.onFailure { exception ->
                        _uiState.update { savedState ->
                            savedState.copy(
                                userMsg = exception.message,
                            )
                        }
                    }
                }
            }
        } catch (_: IllegalArgumentException) {
            _uiState.update { savedState ->
                savedState.copy(
                    userMsg = "Invalid mood data provided."
                )
            }
        }
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
}
