package com.sarmad.moody.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sarmad.moody.ui.theme.AppTheme

@Composable
fun MoodyApp(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppTheme {
        content()
    }
}