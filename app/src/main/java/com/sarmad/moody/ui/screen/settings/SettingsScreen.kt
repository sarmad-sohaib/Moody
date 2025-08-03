package com.sarmad.moody.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarmad.moody.R
import com.sarmad.moody.core.util.toSentenceCase
import com.sarmad.moody.domain.preferences.AppTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SettingsUiState,
    onThemeSelected: (AppTheme) -> Unit,
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.settings),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { showDialog = true },
            text = "Change App Theme",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { showDialog = true },
            text = uiState.appTheme.name.toSentenceCase(),
            style = MaterialTheme.typography.bodySmall
        )

    }

    if (showDialog) {
        ThemeDialog(
            currentTheme = uiState.appTheme,
            onDismiss = { showDialog = false },
            onThemeSelected = {
                showDialog = false
                onThemeSelected(it)
            }
        )
    }
}

@Composable
private fun ThemeDialog(
    currentTheme: AppTheme,
    onDismiss: () -> Unit,
    onThemeSelected: (AppTheme) -> Unit,
) {
    var selectedTheme by rememberSaveable(currentTheme) { mutableStateOf(currentTheme) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choose Theme") },
        text = {
            Column {
                ThemeRadioButton(
                    theme = AppTheme.LIGHT,
                    selectedTheme = selectedTheme
                ) { theme -> selectedTheme = theme }
                ThemeRadioButton(
                    theme = AppTheme.DARK,
                    selectedTheme = selectedTheme
                ) { theme -> selectedTheme = theme }
                ThemeRadioButton(
                    theme = AppTheme.SYSTEM,
                    selectedTheme = selectedTheme
                ) { theme -> selectedTheme = theme }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onThemeSelected(selectedTheme)
                }) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        })
}

@Composable
private fun ThemeRadioButton(
    theme: AppTheme,
    selectedTheme: AppTheme,
    onSelect: (AppTheme) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onSelect(theme) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selectedTheme == theme, onClick = { onSelect(theme) })
        Spacer(Modifier.width(8.dp))
        Text(
            text = when (theme) {
                AppTheme.LIGHT -> "Light Theme"
                AppTheme.DARK -> "Dark Theme"
                AppTheme.SYSTEM -> "Follow System"
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        uiState = SettingsUiState(
            appTheme = AppTheme.SYSTEM,
        ),
        onThemeSelected = {}
    )
}
