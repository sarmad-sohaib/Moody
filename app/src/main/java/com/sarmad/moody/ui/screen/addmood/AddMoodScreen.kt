package com.sarmad.moody.ui.screen.addmood

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarmad.moody.R
import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.domain.dataholder.Weather

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddMoodScreen(
    modifier: Modifier = Modifier,
    moodsMap: List<String> = listOf(
        "Happy",
        "Sad",
        "Angry",
        "Bored",
        "Excited",
        "Relaxed",
        "Anxious",
    ),
    uiState: AddMoodUiState = AddMoodUiState(),
    onSaveLogButtonClick: (Weather?, String) -> Unit,
    onFetchData: () -> Unit,
    onUserMsgShown: () -> Unit,
) {
    var selectedMood by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onFetchData()
    }

    LaunchedEffect(uiState.userMsg != null) {
        val messageResId = uiState.userMsg
        messageResId?.let { messageResId ->
            Toast.makeText(
                context,
                context.getString(messageResId), Toast.LENGTH_SHORT
            ).show()
            onUserMsgShown()
        }

        if (messageResId == R.string.mood_saved_successfully) {
            // Reset the selected mood after saving
            selectedMood = null
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.new_mood_entry),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(R.string.how_are_you_feeling_today),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            style = MaterialTheme.typography.titleLarge
        )

        FlowRow(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            moodsMap.forEach { chipLabel ->
                MoodChip(
                    label = chipLabel,
                    selectedChipLabel = selectedMood,
                ) { clickedLabel ->
                    selectedMood = if (selectedMood == clickedLabel) {
                        null // Deselect if already selected
                    } else {
                        clickedLabel // Select the new mood
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 24.dp,
                    end = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.weather),
                style = MaterialTheme.typography.titleMedium
            )

            if (uiState.isLoading) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
            }
        }

        when {
            uiState.weather != null -> {
                WeatherCard(
                    temperature = "${uiState.weather.temperature} C",
                    location = uiState.weather.location,
                    description = uiState.weather.description,
                )
            }
        }

        // dynamic space to push the button to the bottom
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            onClick = {
                if (selectedMood != null) {
                    onSaveLogButtonClick(
                        uiState.weather,
                        selectedMood ?: ""
                    )
                }
            },
            enabled = selectedMood != null && !uiState.isLoading
        ) {
            Text(
                text = stringResource(R.string.save_mood),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun MoodChip(
    label: String,
    selectedChipLabel: String? = null,
    onClick: (String) -> Unit = {},
) {

    val isSelected = selectedChipLabel == label

    FilterChip(
        onClick = { onClick(label) },
        label = {
            Text(
                text = label,
                style =
                    MaterialTheme.typography.bodyMedium
            )
        },
        selected = isSelected,
    )
}

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    weatherIcon: Painter = painterResource(R.drawable.ic_happy_24),
    temperature: String = "25°C",
    location: String = "San Francisco, CA",
    description: String = "Sunny",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = weatherIcon,
            contentDescription = "Weather icon",
            modifier = Modifier.size(32.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = location,
                modifier = Modifier
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = "$description, $temperature",
                modifier = Modifier
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun WeatherCardPreview() {
    WeatherCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}

@Preview
@Composable
fun MoodChipPreview() {
    MoodChip(
        label = "Happy",
        selectedChipLabel = "Happy",
    )
}

@Preview(
    showBackground = true
)
@Composable
fun AddMoodScreenPreview() {
    AddMoodScreen(
        onSaveLogButtonClick = { _, _ -> },
        onFetchData = {},
        onUserMsgShown = {},
    )
}
