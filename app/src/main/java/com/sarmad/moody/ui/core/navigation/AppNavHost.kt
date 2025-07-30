package com.sarmad.moody.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarmad.moody.ui.screen.AddMoodScreen
import com.sarmad.moody.ui.screen.HistoryScreen
import com.sarmad.moody.ui.screen.InsightsScreen
import com.sarmad.moody.ui.screen.SettingsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Destination = Destination.ADD_MOOD,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HISTORY -> HistoryScreen()
                    Destination.INSIGHTS -> InsightsScreen()
                    Destination.SETTINGS -> SettingsScreen()
                    Destination.ADD_MOOD -> AddMoodScreen()
                }
            }
        }
    }
}
