package com.sarmad.moody.ui.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// Excellent: Navigation management
object Routes {
    const val SETTINGS = "settings"
    const val ADD_MOOD = "add_mood"
    const val INSIGHTS = "insights"
    const val HISTORY = "history"
}

object DestinationTitle {
    const val SETTINGS = "Settings"
    const val ADD_MOOD = "Add Mood"
    const val INSIGHTS = "Insights"
    const val HISTORY = "History"
}

enum class Destination(
    val route: String,
    val icon: ImageVector,
    val title: String,
) {
    ADD_MOOD(Routes.ADD_MOOD, Icons.Default.AddCircle, DestinationTitle.ADD_MOOD),
    HISTORY(Routes.HISTORY, Icons.Default.Info, DestinationTitle.HISTORY),
    INSIGHTS(Routes.INSIGHTS, Icons.Default.Menu, DestinationTitle.INSIGHTS),
    SETTINGS(Routes.SETTINGS, Icons.Default.Settings, DestinationTitle.SETTINGS);
}
