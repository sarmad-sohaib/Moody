package com.sarmad.moody.ui.screen.addmood

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
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarmad.moody.R
import com.sarmad.moody.data.core.dto.WeatherResponse

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddMoodScreen(
    modifier: Modifier = Modifier,
    moodsMap: Map<String, Painter> = mapOf(
        "Happy" to painterResource(R.drawable.ic_happy_24),
        "Sad" to painterResource(R.drawable.ic_sad_24),
        "Angry" to painterResource(R.drawable.ic_angry_24),
        "Bored" to painterResource(R.drawable.ic_bored_24),
        "Excited" to painterResource(R.drawable.ic_excited_24),
        "Relaxed" to painterResource(R.drawable.ic_relax_24),
        "Anxious" to painterResource(R.drawable.ic_anxious_24)
    ),
    uiState: AddMoodUiState = AddMoodUiState(),
    onSaveLogButtonClick: (WeatherResponse?, String) -> Unit,
    onFetchData: () -> Unit,
) {
    var selectedMood by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        onFetchData()
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
            moodsMap.entries.forEach { chipLabel ->
                val label = chipLabel.key
                val icon: Painter = chipLabel.value
                MoodChip(
                    label = label,
                    selectedChipLabel = selectedMood,
                    icon = icon,
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
                    temperature = "${uiState.weather.main.temp} C",
                    location = uiState.weather.name,
                    description = uiState.weather.weather.firstOrNull()?.description ?: "",
                )
            }

            uiState.userMsg != null -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    text = uiState.userMsg
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
    icon: Painter,
    selectedChipLabel: String? = null,
    onClick: (String) -> Unit = {},
) {

    val isSelected = selectedChipLabel == label

    FilterChip(
        onClick = { onClick(label) },
        label = {
            Text(
                text = label,
                style = if (isSelected) {
                    MaterialTheme.typography.bodyLarge
                } else {
                    MaterialTheme.typography.bodyMedium
                }
            )
        },
        selected = isSelected,
        leadingIcon = if (isSelected) {
            {
                Icon(
                    painter = icon,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
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
        icon = painterResource(R.drawable.ic_happy_24),
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
    )
}
