package com.example.fruitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fruitapp.ui.screens.DetailsScreen
import com.example.fruitapp.ui.screens.ListScreen
import com.example.fruitapp.ui.screens.StartScreen
import com.example.fruitapp.ui.theme.FruitAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FruitAppTheme {
                val navController = rememberNavController()
                NavigationComponent(navController = navController)
            }
        }
    }
}


@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = Screen.StartScreen.route){
        composable(route = Screen.StartScreen.route){
                StartScreen(navController = navController)
        }
        composable(route = Screen.DetailsScreen.route + "/{name}",
        arguments = listOf(
            navArgument("name"){
                type = NavType.StringType
            }
        )
        ){entry ->
                DetailsScreen(name = entry.arguments?.getString("name"),
                                navController = navController)
                }
        composable(route = Screen.ListScreen.route){
            ListScreen(navController = navController)
        }
    }
}