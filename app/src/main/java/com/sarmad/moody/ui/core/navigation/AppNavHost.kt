package com.sarmad.moody.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.ui.screen.HistoryScreen
import com.sarmad.moody.ui.screen.InsightsScreen
import com.sarmad.moody.ui.screen.SettingsScreen
import com.sarmad.moody.ui.screen.addmood.AddMoodScreen
import com.sarmad.moody.ui.screen.addmood.AddMoodViewModel

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
                    Destination.HISTORY -> {
                        HistoryScreen()
                    }

                    Destination.INSIGHTS -> {
                        InsightsScreen()
                    }

                    Destination.SETTINGS -> {
                        SettingsScreen()
                    }

                    Destination.ADD_MOOD -> {
                        val moodViewModel = hiltViewModel<AddMoodViewModel>()
                        val uiState by moodViewModel.uiState.collectAsState()
                        AddMoodScreen(
                            uiState = uiState,
                            onFetchData = {
                                moodViewModel.getWeather(
                                    latitude = 37.77,
                                    longitude = -122.41
                                )
                            },
                            onSaveLogButtonClick = { currentWeather, mood ->
                                Mood(
                                    mood = mood,
                                    weatherDescription = currentWeather?.weather?.firstOrNull()?.description
                                        ?: "",
                                    moodIcon = currentWeather?.weather?.firstOrNull()?.icon ?: "",
                                    createdAt = System.currentTimeMillis(),
                                ).also {
                                    moodViewModel.insertMood(
                                        mood = it
                                    )
                                }
//                                navController.navigate(Destination.HISTORY.route) {
//                                    popUpTo(Destination.ADD_MOOD.route) { inclusive = true }
//                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
