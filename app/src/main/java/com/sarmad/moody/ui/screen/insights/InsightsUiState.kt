package com.sarmad.moody.ui.screen.insights

data class InsightsUiState(
    val isLoading: Boolean = false,
    val userMsg: Int? = null,
    val insights: List<String> = emptyList()
)
