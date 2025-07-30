package com.sarmad.moody.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
) {
    var selectedMood by rememberSaveable { mutableStateOf<String?>(null) }

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

        Text(
            text = stringResource(R.string.weather),
            modifier = Modifier
                .padding(start = 16.dp),
            style = MaterialTheme.typography.titleMedium
        )
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
    AddMoodScreen()
}