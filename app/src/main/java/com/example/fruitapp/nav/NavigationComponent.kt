package com.example.fruitapp.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fruitapp.ui.details.DetailsScreen
import com.example.fruitapp.ui.details.DetailsViewModel
import com.example.fruitapp.ui.list.ListScreen
import com.example.fruitapp.ui.list.ListViewModel
import com.example.fruitapp.ui.start.StartScreen
import com.example.fruitapp.ui.start.StartViewModel

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
            val startVM: StartViewModel = hiltViewModel()
            StartScreen(
                state = startVM.state.collectAsState().value,
                onIntent = startVM::onIntent
            )
        }

        composable(route = Screen.DetailsScreen.route + "/{name}") {
            val detailsVM: DetailsViewModel = hiltViewModel()
            DetailsScreen(
                state = detailsVM.state.collectAsState().value,
                onIntent = detailsVM::onIntent
            )
        }

        composable(route = Screen.ListScreen.route) {
            val listVM: ListViewModel = hiltViewModel()
            ListScreen(
                state = listVM.state.collectAsState().value,
                onIntent = listVM::onIntent
            )
        }
    }
}

