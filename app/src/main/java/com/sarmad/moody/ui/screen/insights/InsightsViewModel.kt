package com.sarmad.moody.ui.screen.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmad.moody.R
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.usecase.insights.GetInsightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val dispatchersProviders: CoroutineDispatcherProvider,
    private val getInsightsUseCase: GetInsightsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsightsUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchInsights() = viewModelScope.launch(context = dispatchersProviders.io) {
        _uiState.update { savedState ->
            savedState.copy(
                isLoading = true,
            )
        }
        val insights = getInsightsUseCase()

        if (insights.isEmpty()) {
            _uiState.update { savedState ->
                savedState.copy(
                    isLoading = false,
                    userMsg = R.string.no_insights_found
                )
            }
        } else {
            _uiState.update { savedState ->
                savedState.copy(
                    isLoading = false,
                    insights = insights.map {
                        it.summary
                    },
                    userMsg = null,
                )
            }
        }
    }

    fun userMsgShown() {
        _uiState.update { savedState ->
            savedState.copy(
                userMsg = null
            )
        }
    }
}
