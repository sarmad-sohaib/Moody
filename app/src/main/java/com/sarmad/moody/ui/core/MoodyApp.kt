package com.sarmad.moody.ui.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.sarmad.moody.domain.preferences.AppTheme
import com.sarmad.moody.ui.theme.AppTheme

@Composable
fun MoodyApp(
    currentAppTheme: AppTheme = AppTheme.SYSTEM,
    content: @Composable () -> Unit,
) {
    AppTheme(
        darkTheme = when (currentAppTheme) {
            AppTheme.DARK -> true
            AppTheme.LIGHT -> false
            else -> isSystemInDarkTheme()
        },
    ) {
        content()
    }
}
