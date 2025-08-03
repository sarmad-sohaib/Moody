package com.sarmad.moody.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.preferences.AppTheme
import com.sarmad.moody.domain.usecase.preference.GetAppThemeUseCase
import com.sarmad.moody.domain.usecase.preference.SetAppThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val appTheme: AppTheme = AppTheme.SYSTEM,
    val isThemeLoaded: Boolean = false,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAppTheme()
    }

    fun getAppTheme() = viewModelScope.launch(
        context = dispatcherProvider.io
    ) {
        val userTheme = getAppThemeUseCase()
        _uiState.update { savedState ->
            savedState.copy(
                appTheme = userTheme,
                isThemeLoaded = true
            )
        }
    }

    fun setAppTheme(theme: AppTheme) = viewModelScope.launch(
        context = dispatcherProvider.io
    ) {
        setAppThemeUseCase(
            appTheme = theme
        )
        // Instantly update UI state (so app theme reflects in realtime)
        _uiState.update { current ->
            current.copy(appTheme = theme)
        }
    }
}
