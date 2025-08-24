package com.sarmad.moody.domain.usecase.preference

import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.preferences.AppTheme
import com.sarmad.moody.domain.preferences.UserPreferencesManager
import kotlinx.coroutines.withContext

// Excellent: Interfaces for all usecases
interface SetAppThemeUseCase {
    suspend operator fun invoke(
        appTheme: AppTheme,
    )
}

class DefaultSetAppThemeUseCase(
    private val preferencesManager: UserPreferencesManager,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) : SetAppThemeUseCase {
    override suspend fun invoke(
        appTheme: AppTheme,
    ) =
        withContext(dispatcherProvider.io) {
            preferencesManager.setCurrentAppTheme(
                appTheme = appTheme
            )
        }
}
