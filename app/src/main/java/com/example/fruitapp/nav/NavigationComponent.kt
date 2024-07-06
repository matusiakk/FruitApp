package com.example.fruitapp.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fruitapp.ui.details.DetailsScreen
import com.example.fruitapp.ui.list.ListScreen
import com.example.fruitapp.ui.start.StartScreen


@Composable
fun NavigationComponent(navController: NavHostController) {

    LaunchedEffect(Unit) {
        Navigator.getEventsFlow().collect { event ->
            when (event) {
                is NavEvent.NavigateBack -> navController.popBackStack()
                is NavEvent.NavigateTo -> navController.navigate(event.route)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.route
    ) {

        composable(route = Screen.StartScreen.route) {
            StartScreen()
        }

        composable(route = Screen.DetailsScreen.route + "/{name}") {
            DetailsScreen()
        }

        composable(route = Screen.ListScreen.route + "?favorite={favorite}",
            arguments = listOf(navArgument("favorite") { defaultValue = false })
        ) {
            ListScreen()
        }
    }
}

