package com.sarmad.moody.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarmad.moody.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    uiState: HistoryUiState,
    onFetchData: () -> Unit,
    onFilterSelected: (String) -> Unit,
    onUserMsgShown: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    val filterOptions = remember(uiState.weatherTypes) {
        listOf("All") + uiState.weatherTypes
    }

    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { showMenu = !showMenu },
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_filter_50),
                        contentDescription = stringResource(R.string.filter_weather_types),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    filterOptions.forEach { weatherType ->
                        DropdownMenuItem(
                            text = { Text(weatherType) },
                            onClick = {
                                onFilterSelected(weatherType)
                                showMenu = false
                            }
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButtonPosition = FabPosition.End
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Text(
                text = stringResource(R.string.mood_history),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Selected Filter: ${uiState.selectedWeatherFilter}",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            LazyColumn {
                items(
                    items = uiState.moods,
                    key = { entry -> entry.id }) { entry ->
                    MoodItem(
                        icon = entry.moodIcon,
                        mood = entry.mood,
                        weather = entry.weatherDescription,
                        date = entry.toFormattedDate(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            LaunchedEffect(Unit) {
                onFetchData()
            }

            LaunchedEffect(uiState.userMsg != null) {
                uiState.userMsg?.let { userMsg ->
                    snackBarHostState.showSnackbar(
                        message = context.getString(
                            userMsg
                        ),
                    )
                    onUserMsgShown()
                }
            }
        }
    }
}

@Composable
fun MoodItem(
    icon: String,
    mood: String,
    weather: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = mood,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = weather,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B8E6E)
                )
            }
        }

        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B8E6E)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun MoodItemPreview() {
    MoodItem(
        icon = "😊",
        mood = "Happy",
        weather = "Sunny",
        date = "2023-10-01",
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    HistoryScreen(
        uiState = HistoryUiState(
            isLoadingHistory = false,
            isLoadingWeatherTypes = false,
            userMsg = null,
            moods = emptyList(),
            weatherTypes = listOf("Sunny", "Rainy", "Cloudy"),
            selectedWeatherFilter = "All"
        ),
        onFetchData = {},
        onFilterSelected = {},
        onUserMsgShown = {}
    )
}
