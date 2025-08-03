package com.sarmad.moody.ui.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.sarmad.moody.ui.screen.settings.SettingsViewModel
import com.sarmad.moody.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel(
                LocalActivity.current as ComponentActivity
            )
            val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

            if (uiState.isThemeLoaded) {
                MoodyApp(
                    currentAppTheme = uiState.appTheme
                ) {
                    val navController = rememberNavController()
                    BottomNavigation(
                        navController = navController,
                    ) {
                        finishAffinity()
                    }
                }
            } else {
                // Optionally you can show a splash, for now we'll show a blank screen 
                androidx.compose.foundation.layout.Box(modifier = Modifier)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}
