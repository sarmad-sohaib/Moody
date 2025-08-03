package com.sarmad.moody.ui.core.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarmad.moody.core.util.toSentenceCase
import com.sarmad.moody.data.local.entity.Mood
import com.sarmad.moody.ui.screen.addmood.AddMoodScreen
import com.sarmad.moody.ui.screen.addmood.AddMoodViewModel
import com.sarmad.moody.ui.screen.history.HistoryScreen
import com.sarmad.moody.ui.screen.history.MoodHistoryViewModel
import com.sarmad.moody.ui.screen.insights.InsightsScreen
import com.sarmad.moody.ui.screen.insights.InsightsViewModel
import com.sarmad.moody.ui.screen.settings.SettingsScreen
import com.sarmad.moody.ui.screen.settings.SettingsViewModel

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
                        val moodHistoryViewModel = hiltViewModel<MoodHistoryViewModel>()
                        val uiState by moodHistoryViewModel.uiState.collectAsState()

                        HistoryScreen(
                            uiState = uiState,
                            onFetchData = {
                                moodHistoryViewModel.getUniqueWeatherTypes()
                                moodHistoryViewModel.getAllMoods()
                            },
                            onFilterSelected = { filter ->
                                moodHistoryViewModel.updateWeatherFilter(filter = filter)
                            },
                            onUserMsgShown = {
                                moodHistoryViewModel.userMsgShown()
                            }
                        )
                    }

                    Destination.INSIGHTS -> {
                        val insightsViewModel = hiltViewModel<InsightsViewModel>()
                        val uiState by insightsViewModel.uiState.collectAsState()
                        InsightsScreen(
                            uiState = uiState,
                            onFetchInsights = {
                                insightsViewModel.fetchInsights()
                            }
                        )
                    }

                    Destination.SETTINGS -> {
                        val settingsViewModel: SettingsViewModel = hiltViewModel(
                            LocalActivity.current as ComponentActivity
                        )
                        val uiState by settingsViewModel.uiState.collectAsState()
                        SettingsScreen(
                            uiState = uiState,
                            onThemeSelected = { theme ->
                                settingsViewModel.setAppTheme(
                                    theme = theme
                                )

                            },
                        )
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
                                    weatherDescription = currentWeather
                                        ?.description
                                        ?.toSentenceCase()
                                        ?: "",
                                    moodIcon = "",
                                    createdAt = System.currentTimeMillis(),
                                ).also {
                                    moodViewModel.insertMood(
                                        mood = it
                                    )
                                }
                            },
                            onUserMsgShown = {
                                moodViewModel.userMsgShown()
                            }
                        )
                    }
                }
            }
        }
    }
}
