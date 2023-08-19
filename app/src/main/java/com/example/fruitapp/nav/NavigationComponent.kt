package com.example.fruitapp.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fruitapp.ui.details.DetailsScreen
import com.example.fruitapp.ui.details.DetailsViewModel
import com.example.fruitapp.ui.list.ListScreen
import com.example.fruitapp.ui.list.ListViewModel
import com.example.fruitapp.ui.start.StartScreen
import com.example.fruitapp.ui.start.StartViewModel

@Composable
fun NavigationComponent(navController: NavHostController) {

    val startVM: StartViewModel = hiltViewModel()
    startVM.navController = navController

    val detailsVM: DetailsViewModel = hiltViewModel()
    detailsVM.navController = navController

    val listVM: ListViewModel = hiltViewModel()
    listVM.navController = navController

    NavHost(navController = navController,
        startDestination = Screen.StartScreen.route) {

        composable(route = Screen.StartScreen.route) {
            StartScreen(
                state = startVM.state.collectAsState().value,
                onIntent = startVM::setIntent
            )
        }

        composable(route = Screen.DetailsScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                }
            )
        ){entry ->
            DetailsScreen(name = entry.arguments?.getString("name"),
                         state = detailsVM.state.collectAsState().value,
                         onIntent = detailsVM::setIntent)
        }

        composable(route = Screen.ListScreen.route){
            ListScreen(state = listVM.state.collectAsState().value,
                        onIntent = listVM::setIntent)
        }
    }
}

