package com.example.fruitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.fruitapp.nav.NavigationComponent
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

