package com.example.fruitapp.nav

sealed class Screen (val route: String) {
    object StartScreen : Screen("start")
    object DetailsScreen : Screen("details")
    object ListScreen : Screen("list")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
