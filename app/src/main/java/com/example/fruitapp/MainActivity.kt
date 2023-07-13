package com.example.fruitapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fruitapp.ui.screens.DetailsScreen
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
                val vm = viewModel<MainViewModel>()
                NavigationComponent(navController = navController, vm = vm)
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController,
                        vm: MainViewModel) {
    NavHost(navController = navController,
        startDestination = "start"){
        composable(route = "start"){
                StartScreen(onClickButton = { str ->
                    vm.getFruitDetails(str)

                    navController.navigate(route = "details/$str")
                })
        }
        composable(route = "details/{name}") {
            val fruit = vm.state.value.fruit

                if (fruit != null) {
                    DetailsScreen(fruit = fruit)
                }
                }
            }
        }