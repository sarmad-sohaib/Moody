package com.sarmad.moody.ui.core

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sarmad.moody.ui.core.navigation.AppNavHost
import com.sarmad.moody.ui.core.navigation.Destination

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Destination = Destination.ADD_MOOD,
    onBackFromStartDestination: () -> Unit,
) {
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    // This state tracks the *actual current route* from the NavController
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Update selectedDestinationIndex when the route changes due to navigation
    // This ensures the bottom bar highlights the correct tab even with deep links or programmatic navigation
    // And also helps in our back handling logic
    LaunchedEffect(currentRoute) {
        currentRoute?.let { route ->
            val matchingDestination =
                Destination.entries.find { it.route == route || navController.graph.findNode(it.route)?.hierarchy?.any { dest -> dest.route == route } == true }
            matchingDestination?.let {
                selectedDestination = it.ordinal
            }
        }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            // It ensures that when you click a tab, you either navigate to its start destination
                            // or pop up to it if you're already in its stack, and it saves the back stack.
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true // Save state of popped destinations
                                }
                                launchSingleTop =
                                    true // Avoid multiple copies of the same destination
                                restoreState =
                                    true // Restore state if navigating to a previously visited destination
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.name
                            )
                        },
                        label = { Text(text = destination.title) }
                    )
                }
            }
        }
    ) { innerPadding ->

        // Custom Back Handling Logic
        BackHandler(enabled = true) { // Always enabled when this Scaffold is active
            val currentSelectedTabStartRoute = Destination.entries[selectedDestination].route

            if (currentRoute == currentSelectedTabStartRoute) {
                // If we are on the start destination of the currently selected tab
                if (currentRoute == startDestination.route) {
                    // And this tab is also the overall start destination of the BottomNavigation,
                    // call the onBackFromStartDestination callback (e.g., to finish activity or navigate up)
                    onBackFromStartDestination()
                } else {
                    // If this tab is NOT the overall start destination,
                    // navigate to the overall start destination tab.
                    navController.navigate(startDestination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    // Update the selected index to the overall start destination
                    selectedDestination = startDestination.ordinal
                }
            } else {
                // If we are deeper in the back stack of the current tab,
                // simply pop the back stack.
                // If the pop takes us to a different tab's screen, the LaunchedEffect
                // will update selectedDestinationIndex.
                // If pop takes us to the start destination of the current tab, the next back press
                // will be handled by the logic above.
                if (!navController.popBackStack()) {
                    // If popBackStack returns false, it means there's nothing to pop,
                    // which shouldn't happen if currentRoute != currentSelectedTabStartRoute.
                    // As a fallback, or if this is the very root, call onBackFromStartDestination.
                    onBackFromStartDestination()
                }
            }
        }


        AppNavHost(
            modifier = Modifier.padding(paddingValues = innerPadding),
            navController = navController,
            startDestination = startDestination
        )
    }
}
