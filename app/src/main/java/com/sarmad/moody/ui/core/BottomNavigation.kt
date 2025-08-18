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

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

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
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop =
                                    true
                                restoreState =
                                    true
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

        BackHandler(enabled = true) {
            val currentSelectedTabStartRoute = Destination.entries[selectedDestination].route

            if (currentRoute == currentSelectedTabStartRoute) {
                if (currentRoute == startDestination.route) {
                    onBackFromStartDestination()
                } else {
                    navController.navigate(startDestination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    selectedDestination = startDestination.ordinal
                }
            } else {
                if (!navController.popBackStack()) {
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
