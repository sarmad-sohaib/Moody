package com.sarmad.moody.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmad.moody.R
import com.sarmad.moody.core.util.Constants
import com.sarmad.moody.data.local.entity.mood.Mood
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.usecase.mood.GetAllMoodsUseCase
import com.sarmad.moody.domain.usecase.weather.GetUniqueWeatherTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryUiState(
    val isLoadingHistory: Boolean = false,
    val isLoadingWeatherTypes: Boolean = false,
    val userMsg: Int? = null,
    val moods: List<Mood> = emptyList(),
    val weatherTypes: List<String> = emptyList(),
    val selectedWeatherFilter: String = Constants.ALL_WEATHER_TYPE,
)

@HiltViewModel
class MoodHistoryViewModel @Inject constructor(
    private val getAllMoodsUseCase: GetAllMoodsUseCase,
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val getUniqueWeatherTypesUseCase: GetUniqueWeatherTypesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    val allMoods = mutableListOf<Mood>()

    fun getAllMoods() = viewModelScope.launch(dispatcherProvider.io) {
        _uiState.update { savedState ->
            savedState.copy(isLoadingHistory = true)
        }
        getAllMoodsUseCase().collect { moods ->
            if (moods.isEmpty()) {

                allMoods.clear()

                _uiState.update { savedState ->
                    savedState.copy(
                        isLoadingHistory = false,
                        moods = emptyList(),
                        userMsg = R.string.no_moods_found,
                    )
                }
            } else {
                allMoods.clear()
                allMoods.addAll(moods)
                _uiState.update { savedState ->
                    savedState.copy(
                        isLoadingHistory = false,
                        moods = filterMoodsByWeatherType(
                            filter = savedState.selectedWeatherFilter
                        ),
                        userMsg = null,
                    )
                }
            }
        }
    }

    fun getUniqueWeatherTypes() = viewModelScope.launch(dispatcherProvider.io) {
        _uiState.update { savedState ->
            savedState.copy(isLoadingWeatherTypes = true)
        }
        getUniqueWeatherTypesUseCase().collect { weatherTypes ->
            if (weatherTypes.isEmpty()) {
                _uiState.update { savedState ->
                    savedState.copy(
                        isLoadingWeatherTypes = false,
                        weatherTypes = emptyList(),
                        userMsg = R.string.no_weather_types_found,
                    )
                }
            } else {
                _uiState.update { savedState ->
                    savedState.copy(
                        isLoadingWeatherTypes = false,
                        weatherTypes = weatherTypes,
                        userMsg = null,
                    )
                }
            }
        }
    }

    fun updateWeatherFilter(filter: String) {
        _uiState.update { savedState ->
            savedState.copy(
                selectedWeatherFilter = filter,
                moods = filterMoodsByWeatherType(
                    filter = filter
                )
            )
        }
    }

    private fun filterMoodsByWeatherType(
        filter: String,
    ): List<Mood> {
        return if (filter == Constants.ALL_WEATHER_TYPE) {
            allMoods
        } else {
            allMoods.filter { mood ->
                mood.weatherDescription.equals(filter, ignoreCase = true)
            }
        }
    }

    fun userMsgShown() {
        _uiState.update { savedState ->
            savedState.copy(userMsg = null)
        }
    }
}
