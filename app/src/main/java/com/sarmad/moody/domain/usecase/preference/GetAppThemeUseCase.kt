package com.sarmad.moody.domain.usecase.preference

import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import com.sarmad.moody.domain.preferences.AppTheme
import com.sarmad.moody.domain.preferences.UserPreferencesManager
import kotlinx.coroutines.withContext

interface GetAppThemeUseCase {
    suspend operator fun invoke(): AppTheme
}

class DefaultGetAppThemeUseCase(
    private val preferencesManager: UserPreferencesManager,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) : GetAppThemeUseCase {
    override suspend fun invoke() =
        withContext(dispatcherProvider.io) {
            preferencesManager.getCurrentAppTheme()
        }
}
